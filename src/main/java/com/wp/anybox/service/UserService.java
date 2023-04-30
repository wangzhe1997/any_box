package com.wp.anybox.service;

import com.wp.anybox.entity.User;
import com.wp.anybox.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Transactional(readOnly = true)
    public List<User> findByLoginName(String loginName) {
        return userMapper.findByLoginName(loginName);
    }

    @Transactional(readOnly = true)
    public void insertUser(User user) {
        userMapper.insertUser(user);
    }
    @Transactional(readOnly = false)
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }
}

