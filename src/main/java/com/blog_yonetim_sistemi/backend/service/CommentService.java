package com.blog_yonetim_sistemi.backend.service;

import com.blog_yonetim_sistemi.backend.dto.request.CommentRequest;
import com.blog_yonetim_sistemi.backend.dto.response.CommentResponse;

import java.util.List;

public interface CommentService {

    CommentResponse createComment(CommentRequest request, String username);

    List<CommentResponse> getCommentsByPostId(Long postId);
}