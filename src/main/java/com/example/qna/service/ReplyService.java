package com.example.qna.service;

import com.example.qna.entity.Reply;

import java.util.List;

public interface ReplyService {
    List<Reply> findByDiscussionId(int discussionId);
    void create(Reply reply);
}
