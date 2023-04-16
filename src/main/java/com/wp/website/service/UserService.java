package com.wp.website.service;

import com.wp.website.entity.User;
import com.wp.website.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public List<User> findByLoginName(String loginName) {
        return userMapper.findByLoginName(loginName);
    }
}

