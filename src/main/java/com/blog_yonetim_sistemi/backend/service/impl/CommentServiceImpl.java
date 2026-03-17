package com.blog_yonetim_sistemi.backend.service.impl;

import com.blog_yonetim_sistemi.backend.dto.request.CommentRequest;
import com.blog_yonetim_sistemi.backend.dto.response.CommentResponse;
import com.blog_yonetim_sistemi.backend.entity.Comment;
import com.blog_yonetim_sistemi.backend.entity.Post;
import com.blog_yonetim_sistemi.backend.entity.User;
import com.blog_yonetim_sistemi.backend.mapper.CommentMapper;
import com.blog_yonetim_sistemi.backend.repository.CommentRepository;
import com.blog_yonetim_sistemi.backend.repository.PostRepository;
import com.blog_yonetim_sistemi.backend.repository.UserRepository;
import com.blog_yonetim_sistemi.backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentResponse createComment(CommentRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        Post post = postRepository.findByIdAndActiveTrue(request.getPostId())
                .orElseThrow(() -> new RuntimeException("Post bulunamadı veya silinmiş"));

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setAuthor(user);
        comment.setPost(post);

        return commentMapper.toResponse(commentRepository.save(comment));
    }

    @Override
    public List<CommentResponse> getCommentsByPostId(Long postId) {
        return commentMapper.toResponseList(commentRepository.findByPostIdOrderByCreatedDateDesc(postId));
    }
}