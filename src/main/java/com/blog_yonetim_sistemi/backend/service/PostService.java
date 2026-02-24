package com.blog_yonetim_sistemi.backend.service;

import com.blog_yonetim_sistemi.backend.entity.Post;
import org.springframework.data.domain.Page;

public interface PostService {

    Post createPost(Post post, String username);

    Post updatePost(Long id, Post post, String username);

    void deletePost(Long id, String username);

    Post getPostById(Long id);

    Page<Post> getAllActivePosts(int page, int size);

    Page<Post> getPostsByCategory(Long categoryId, int page, int size);

    Page<Post> getPostsByTag(Long tagId, int page, int size);

    Page<Post> searchByTitle(String title, int page, int size);
}