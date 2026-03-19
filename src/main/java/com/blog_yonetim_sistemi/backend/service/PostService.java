package com.blog_yonetim_sistemi.backend.service;

import com.blog_yonetim_sistemi.backend.dto.request.PostRequest;
import com.blog_yonetim_sistemi.backend.dto.response.PostResponse;
import com.blog_yonetim_sistemi.backend.dto.response.UserResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    PostResponse createPost(PostRequest request, String username);

    PostResponse updatePost(Long id, PostRequest request, String username);

    void deletePost(Long id, String username);

    PostResponse getPostById(Long id);

    Page<PostResponse> getAllActivePosts(int page, int size);

    Page<PostResponse> getPostsByCategory(Long categoryId, int page, int size);

    Page<PostResponse> getPostsByTag(Long tagId, int page, int size);

    Page<PostResponse> searchByTitle(String title, int page, int size);

    // --- YENİ EKLENEN BEĞENİ VE KAYDETME METODLARI ---

    void toggleLike(Long postId, String username);

    List<UserResponse> getPostLikers(Long postId, String username);

    void toggleBookmark(Long postId, String username);

    List<PostResponse> getMyBookmarkedPosts(String username);
}