package com.example.qna.service;

import com.example.qna.entity.User;

public interface UserService {
    User findByUsername(String username);
    void register(User user);
}
