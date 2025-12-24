package com.example.qna.mapper;

import com.example.qna.entity.Discussion;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DiscussionMapper {
    @Select("SELECT id, title, content, author, create_time FROM discussions ORDER BY create_time DESC")
    @Results(id = "DiscussionResult", value = {
            @Result(column = "create_time", property = "createTime")
    })
    List<Discussion> findAll();

    @Select("SELECT id, title, content, author, create_time FROM discussions WHERE id = #{id}")
    @ResultMap("DiscussionResult")
    Discussion findById(@Param("id") Integer id);

    @Insert("INSERT INTO discussions(title, content, author, create_time) VALUES(#{title}, #{content}, #{author}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Discussion discussion);
}
