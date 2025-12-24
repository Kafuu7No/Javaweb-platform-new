package com.example.qna.service.impl;

import com.example.qna.entity.User;
import com.example.qna.mapper.UserMapper;
import com.example.qna.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public void register(User user) {
        userMapper.insert(user);
    }
}
