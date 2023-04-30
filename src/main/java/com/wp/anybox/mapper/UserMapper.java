package com.wp.anybox.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wp.anybox.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from USERS where phone = #{loginName} or email = #{loginName} or qq = #{loginName}")
    List<User> findByLoginName(String loginName);
    @Insert("INSERT INTO USERS(name, password, email, phone, avatar, avatar_source, birthday, sex, sign, qq, qq_name, wechat, wechat_name, register_time, modify_time, last_login_time, state VALUES (#{name}, #{password}, #{email}, #{phone}, #{avatar}, #{avatarSource}, #{birthday}, #{sex}, #{sign}, #{qq}, #{qqName}, #{wechat}, #{wechatName}, #{registerTime}, #{modifyTime}, #{lastLoginTime}, #{state})")
    int insertUser(User user);
    @Update("UPDATE USERS SET name=#{name}, password=#{password}, email=#{email}, phone=#{phone}, avatar=#{avatar}, avatar_source=#{avatarSource}, birthday=#{birthday}, sex=#{sex}, sign=#{sign}, qq=#{qq}, qq_name=#{qqName}, wechat=#{wechat}, wechat_name=#{wechatName}, register_time=#{registerTime}, modify_time=#{modifyTime}, last_login_time=#{lastLoginTime}, state=#{state} WHERE id=#{id}")
    int updateUser(User user);

}