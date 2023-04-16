package com.wp.website.mapper;

import com.wp.website.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    //增
    void addUser(User user);
    //删
    void deleteUser(User user);
    //改
    void updateUser(User user);
    //查
    User getUserById(Long id);

    List<User> findByLoginName(String loginName);
}