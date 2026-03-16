package com.blog_yonetim_sistemi.backend.mapper;

import com.blog_yonetim_sistemi.backend.dto.request.CommentRequest;
import com.blog_yonetim_sistemi.backend.dto.response.CommentResponse;
import com.blog_yonetim_sistemi.backend.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "author.username", target = "authorUsername")
    CommentResponse toResponse(Comment comment);

    List<CommentResponse> toResponseList(List<Comment> comments);

    Comment toEntity(CommentRequest request);
}