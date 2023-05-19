package com.wp.anybox.controller;

import com.alibaba.fastjson.JSONObject;
import com.wp.anybox.entity.User;
import com.wp.anybox.service.UserService;
import com.wp.anybox.utils.HttpUtil;
import io.micrometer.common.util.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.wp.anybox.utils.CaptchaUtil.*;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

    //private final int COOKIE_MAX_AGE = 30 * 24 * 60 * 60;// 设置 cookie 有效期为 30 天

    @PostMapping("/login")
    public JSONObject login(@RequestBody JSONObject loginJson) {
        List<User> userList = userService.findByLoginName(loginJson.getString("loginName"));
        JSONObject userLoginInfo = new JSONObject();
        if (userList != null && userList.size() > 0) {
            for (User user : userList) {
                String registerTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.getRegisterTime());
                System.err.println(registerTime);
                if (user.getPassword().equals(md5(loginJson.getString("password"), registerTime))) {
                    user = getQQInfo(user);
                    user.setLastLoginTime(new Date());
                    userService.updateUser(user);
                    userLoginInfo.put("name", user.getName());
                    userLoginInfo.put("avatar", user.getAvatar());
                    userLoginInfo.put("userToken", user.getPassword());
                    userLoginInfo.put("sex", user.getSex());
                    userLoginInfo.put("birthday", user.getBirthday());
                    return userLoginInfo;
//                    session.setAttribute("user", user);
//                    Cookie cookie = new Cookie("userToken", user.getPassword());
//                    cookie.setMaxAge(COOKIE_MAX_AGE); // 设置 cookie 的有效期为 30 天
//                    cookie.setPath("/");  //设置cookie的作用范围是整个站点（即根路径下所有资源都可以访问该cookie）
//                    response.addCookie(cookie);
                }
            }
            userLoginInfo.put("errorMsg", "密码错误！");
        } else {
            userLoginInfo.put("errorMsg", "用户不存在！");
        }
        return userLoginInfo;
    }

    private User getQQInfo(User user) {
        if (StringUtils.isNotBlank(user.getQq())) {
            String qqAvatar = HttpUtil.getImgBase64("https://q.qlogo.cn/headimg_dl?dst_uin=" + user.getQq() + "&spec=640");
            if (StringUtils.isBlank(user.getAvatar()) || user.getAvatarSource() == 2 && !user.getAvatar().equals(qqAvatar)) {
                user.setAvatar(qqAvatar);
                user.setAvatarSource(2);
                user.setModifyTime(new Date());
            }
            String qqNameStr = HttpUtil.doGet("https://r.qzone.qq.com/fcg-bin/cgi_get_portrait.fcg?uins=" + user.getQq(), null);
            qqNameStr = qqNameStr.substring(qqNameStr.indexOf(",\"") + 2);
            String qqName = qqNameStr.substring(0, qqNameStr.indexOf("\""));
            try {
                qqName = URLDecoder.decode(qqName, "GBK");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            if (StringUtils.isBlank(user.getName())) {
                user.setName(qqName);
                user.setModifyTime(new Date());
            } else if (StringUtils.isBlank(user.getQqName()) || !user.getQqName().equals(qqName)) {
                user.setQqName(qqName);
                user.setModifyTime(new Date());
            }
        }
        return user;
    }

    @GetMapping("/verify")
    public String verify() {
        String captchaText = getRandomString(6); // 生成 6 位随机验证码
        BufferedImage captchaImage = generateCaptcha(captchaText, 200, 50); // 生成验证码图片（200×50）
        return bufferedImageToBase64(captchaImage);
    }

    public static String md5(String password, String salt) {
        //加密方式
        String hashAlgorithmName = "MD5";
        //盐：相同密码使用不同的盐加密后的结果不同
        ByteSource byteSalt = ByteSource.Util.bytes(salt);
        //加密次数
        int hashIterations = 3;
        SimpleHash result = new SimpleHash(hashAlgorithmName, password, byteSalt, hashIterations);
        return result.toString();
    }


}