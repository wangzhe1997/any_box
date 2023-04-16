package com.wp.website.controller;

import com.alibaba.fastjson.JSONObject;
import com.wp.website.entity.User;
import com.wp.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.Arrays;
import java.util.List;


@RestController
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/login")
    public JSONObject login(@RequestParam String loginName, @RequestParam String password) {
        JSONObject userInfoJSON = new JSONObject();
        List<User> userList = userService.findByLoginName(loginName);
        if(userList!=null && userList.size()>0){
            for(User user:userList){
                if(password.equals(md5(user.getPassword(),user.getRegisterTime().toString()))){
                    userInfoJSON.put("name",user.getName());
                    userInfoJSON.put("sign",user.getSign());
                    userInfoJSON.put("birthday",user.getBirthday());
                    return userInfoJSON;
                }
            }
            userInfoJSON.put("info", "用户不存在！");
        }else{
            userInfoJSON.put("info", "密码错误！");
        }
        return userInfoJSON;
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }


    public static String md5(String password, String salt){
        //加密方式
        String hashAlgorithmName = "MD5";
        //盐：相同密码使用不同的盐加密后的结果不同
        ByteSource byteSalt = ByteSource.Util.bytes(salt);
        //加密次数
        int hashIterations = 3;
        SimpleHash result = new SimpleHash(hashAlgorithmName, password, byteSalt, hashIterations);
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(md5("Wp19971012@","2023-05-01 00:00:00"));
    }
}