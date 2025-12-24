package com.example.qna.service.impl;

import com.example.qna.entity.Discussion;
import com.example.qna.mapper.DiscussionMapper;
import com.example.qna.service.DiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DiscussionServiceImpl implements DiscussionService {
    @Autowired
    private DiscussionMapper discussionMapper;

    @Override
    public List<Discussion> listAll() {
        return discussionMapper.findAll();
    }

    @Override
    public Discussion findById(int id) {
        return discussionMapper.findById(id);
    }

    @Override
    public void create(Discussion discussion) {
        if (discussion.getCreateTime() == null) {
            discussion.setCreateTime(LocalDateTime.now());
        }
        discussionMapper.insert(discussion);
    }
}
