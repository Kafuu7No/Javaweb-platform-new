package com.example.qna.service.impl;

import com.example.qna.entity.Reply;
import com.example.qna.mapper.ReplyMapper;
import com.example.qna.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    private ReplyMapper replyMapper;

    @Override
    public List<Reply> findByDiscussionId(int discussionId) {
        return replyMapper.findByDiscussionId(discussionId);
    }

    @Override
    public void create(Reply reply) {
        if (reply.getCreateTime() == null) {
            reply.setCreateTime(LocalDateTime.now());
        }
        replyMapper.insert(reply);
    }
}
