package com.blog_yonetim_sistemi.backend.controller;

import com.blog_yonetim_sistemi.backend.dto.request.CommentRequest;
import com.blog_yonetim_sistemi.backend.dto.response.CommentResponse;
import com.blog_yonetim_sistemi.backend.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // Yorum ekleme (Sadece giriş yapmış kullanıcılar)
    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @Valid @RequestBody CommentRequest request,
            Authentication authentication) {

        String username = authentication.getName();
        return ResponseEntity.status(201).body(commentService.createComment(request, username));
    }

    // Bir postun yorumlarını listeleme (Herkese açık)
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }
}