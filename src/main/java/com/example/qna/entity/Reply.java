package com.example.qna.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
    private Integer id;
    private Integer discussionId;
    private String content;
    private String author;
    private LocalDateTime createTime;
    // getters/setters
}
