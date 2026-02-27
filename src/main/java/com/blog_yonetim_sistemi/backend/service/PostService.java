package com.blog_yonetim_sistemi.backend.service;

import com.blog_yonetim_sistemi.backend.dto.request.PostRequest;
import com.blog_yonetim_sistemi.backend.dto.response.PostResponse;
import org.springframework.data.domain.Page;

public interface PostService {

    PostResponse createPost(PostRequest request, String username);

    PostResponse updatePost(Long id, PostRequest request, String username);

    void deletePost(Long id, String username);

    PostResponse getPostById(Long id);

    Page<PostResponse> getAllActivePosts(int page, int size);

    Page<PostResponse> getPostsByCategory(Long categoryId, int page, int size);

    Page<PostResponse> getPostsByTag(Long tagId, int page, int size);

    Page<PostResponse> searchByTitle(String title, int page, int size);
}