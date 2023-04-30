package com.wp.anybox.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;

@TableName("USERS")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String password;
    private String email;
    private String phone;
    @TableField(value = "avatar", jdbcType = JdbcType.CLOB)
    private String avatar;
    private int avatarSource;
    private Date birthday;
    private int sex;
    private String sign;
    private String qq;
    private String qqName;
    private String wechat;
    private String wechatName;
    private Date registerTime;
    private Date modifyTime;
    private Date lastLoginTime;
    private int state;

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(final String avatar) {
        this.avatar = avatar;
    }

    public int getAvatarSource() {
        return this.avatarSource;
    }

    public void setAvatarSource(final int avatarSource) {
        this.avatarSource = avatarSource;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(final Date birthday) {
        this.birthday = birthday;
    }

    public int getSex() {
        return this.sex;
    }

    public void setSex(final int sex) {
        this.sex = sex;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(final String sign) {
        this.sign = sign;
    }

    public String getQq() {
        return this.qq;
    }

    public void setQq(final String qq) {
        this.qq = qq;
    }

    public String getQqName() {
        return this.qqName;
    }

    public void setQqName(final String qqName) {
        this.qqName = qqName;
    }

    public String getWechat() {
        return this.wechat;
    }

    public void setWechat(final String wechat) {
        this.wechat = wechat;
    }

    public String getWechatName() {
        return this.wechatName;
    }

    public void setWechatName(final String wechatName) {
        this.wechatName = wechatName;
    }

    public Date getRegisterTime() {
        return this.registerTime;
    }

    public void setRegisterTime(final Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(final Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getLastLoginTime() {
        return this.lastLoginTime;
    }

    public void setLastLoginTime(final Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getState() {
        return this.state;
    }

    public void setState(final int state) {
        this.state = state;
    }
}
