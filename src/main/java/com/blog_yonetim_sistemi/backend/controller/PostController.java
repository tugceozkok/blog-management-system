package com.blog_yonetim_sistemi.backend.controller;

import com.blog_yonetim_sistemi.backend.dto.request.PostRequest;
import com.blog_yonetim_sistemi.backend.dto.response.PostResponse;
import com.blog_yonetim_sistemi.backend.dto.response.UserResponse;
import com.blog_yonetim_sistemi.backend.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @Valid @RequestBody PostRequest request,
            Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.status(201).body(postService.createPost(request, username));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostRequest request,
            Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(postService.updatePost(id, request, username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long id,
            Authentication authentication) {
        postService.deletePost(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping
    public ResponseEntity<Page<PostResponse>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(postService.getAllActivePosts(page, size));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<PostResponse>> getByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(postService.getPostsByCategory(categoryId, page, size));
    }

    @GetMapping("/tag/{tagId}")
    public ResponseEntity<Page<PostResponse>> getByTag(
            @PathVariable Long tagId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(postService.getPostsByTag(tagId, page, size));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PostResponse>> searchByTitle(
            @RequestParam String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(postService.searchByTitle(title, page, size));
    }

    // --- YENİ EKLENEN BEĞENİ VE KAYDETME ENDPOINTLERİ ---

    @PostMapping("/{id}/like")
    public ResponseEntity<String> toggleLike(@PathVariable Long id, Authentication authentication) {
        postService.toggleLike(id, authentication.getName());
        return ResponseEntity.ok("Beğeni durumu güncellendi.");
    }

    @GetMapping("/{id}/likes")
    public ResponseEntity<List<UserResponse>> getPostLikers(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(postService.getPostLikers(id, authentication.getName()));
    }

    @PostMapping("/{id}/bookmark")
    public ResponseEntity<String> toggleBookmark(@PathVariable Long id, Authentication authentication) {
        postService.toggleBookmark(id, authentication.getName());
        return ResponseEntity.ok("Daha sonra oku listesi güncellendi.");
    }

    @GetMapping("/bookmarks")
    public ResponseEntity<List<PostResponse>> getMyBookmarkedPosts(Authentication authentication) {
        return ResponseEntity.ok(postService.getMyBookmarkedPosts(authentication.getName()));
    }
}