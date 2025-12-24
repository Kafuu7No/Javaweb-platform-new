package com.example.qna.service;

import com.example.qna.entity.Discussion;

import java.util.List;

public interface DiscussionService {
    List<Discussion> listAll();
    Discussion findById(int id);
    void create(Discussion discussion);
}
