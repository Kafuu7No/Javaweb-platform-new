package com.example.qna.mapper;

import com.example.qna.entity.Reply;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReplyMapper {
    @Select("SELECT id, discussion_id, content, author, create_time FROM replies WHERE discussion_id = #{discussionId} ORDER BY create_time")
    @Results(id = "ReplyResult", value = {
            @Result(column = "discussion_id", property = "discussionId"),
            @Result(column = "create_time", property = "createTime")
    })
    List<Reply> findByDiscussionId(@Param("discussionId") Integer discussionId);

    @Insert("INSERT INTO replies(discussion_id, content, author, create_time) VALUES(#{discussionId}, #{content}, #{author}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Reply reply);
}
