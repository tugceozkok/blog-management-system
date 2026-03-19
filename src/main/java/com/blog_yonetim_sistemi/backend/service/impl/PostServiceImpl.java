package com.blog_yonetim_sistemi.backend.service.impl;

import com.blog_yonetim_sistemi.backend.dto.request.PostRequest;
import com.blog_yonetim_sistemi.backend.dto.response.PostResponse;
import com.blog_yonetim_sistemi.backend.dto.response.UserResponse;
import com.blog_yonetim_sistemi.backend.entity.Post;
import com.blog_yonetim_sistemi.backend.entity.PostStatus;
import com.blog_yonetim_sistemi.backend.entity.User;
import com.blog_yonetim_sistemi.backend.mapper.PostMapper;
import com.blog_yonetim_sistemi.backend.mapper.UserMapper;
import com.blog_yonetim_sistemi.backend.repository.*;
import com.blog_yonetim_sistemi.backend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final PostMapper postMapper;
    private final UserMapper userMapper;

    @Override
    public PostResponse createPost(PostRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        if (request.getTagIds() == null || request.getTagIds().isEmpty()) {
            throw new RuntimeException("Post en az 1 tag içermelidir");
        }

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthor(user);
        post.setActive(true);

        if (request.getStatus() != null && request.getStatus().equalsIgnoreCase("PENDING")) {
            post.setStatus(PostStatus.PENDING);
        } else {
            post.setStatus(PostStatus.DRAFT);
        }

        post.setCategory(categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı")));

        post.setTags(new HashSet<>(tagRepository.findAllById(request.getTagIds())));

        return postMapper.toResponse(postRepository.save(post));
    }

    @Override
    public PostResponse updatePost(Long id, PostRequest request, String username) {
        Post existingPost = postRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new RuntimeException("Post bulunamadı"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        boolean isAuthor = existingPost.getAuthor().getUsername().equals(username);
        boolean isEditor = user.getRole().name().equals("ROLE_EDITOR");
        boolean isAdmin = user.getRole().name().equals("ROLE_ADMIN");

        if (!isAuthor && !isEditor && !isAdmin) {
            throw new RuntimeException("Bu postu güncelleme yetkiniz yok");
        }

        existingPost.setTitle(request.getTitle());
        existingPost.setContent(request.getContent());

        if (request.getStatus() != null) {
            existingPost.setStatus(PostStatus.valueOf(request.getStatus().toUpperCase()));
        }

        existingPost.setCategory(categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı")));

        existingPost.setTags(new HashSet<>(tagRepository.findAllById(request.getTagIds())));

        return postMapper.toResponse(postRepository.save(existingPost));
    }

    @Override
    public void deletePost(Long id, String username) {
        Post post = postRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new RuntimeException("Post bulunamadı"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        boolean isAuthor = post.getAuthor().getUsername().equals(username);
        boolean isEditor = user.getRole().name().equals("ROLE_EDITOR");
        boolean isAdmin = user.getRole().name().equals("ROLE_ADMIN");

        if (!isAuthor && !isEditor && !isAdmin) {
            throw new RuntimeException("Bu postu silme yetkiniz yok");
        }

        post.setActive(false);
        postRepository.save(post);
    }

    @Override
    public PostResponse getPostById(Long id) {
        return postMapper.toResponse(
                postRepository.findByIdAndActiveTrueAndStatus(id, PostStatus.PUBLISHED)
                        .orElseThrow(() -> new RuntimeException("Post bulunamadı veya yayında değil"))
        );
    }

    @Override
    public Page<PostResponse> getAllActivePosts(int page, int size) {
        return postRepository.findByActiveTrueAndStatus(PostStatus.PUBLISHED, PageRequest.of(page, size))
                .map(postMapper::toResponse);
    }

    @Override
    public Page<PostResponse> getPostsByCategory(Long categoryId, int page, int size) {
        return postRepository.findByCategoryIdAndActiveTrueAndStatus(categoryId, PostStatus.PUBLISHED, PageRequest.of(page, size))
                .map(postMapper::toResponse);
    }

    @Override
    public Page<PostResponse> getPostsByTag(Long tagId, int page, int size) {
        return postRepository.findByTagsIdAndActiveTrueAndStatus(tagId, PostStatus.PUBLISHED, PageRequest.of(page, size))
                .map(postMapper::toResponse);
    }

    @Override
    public Page<PostResponse> searchByTitle(String title, int page, int size) {
        return postRepository.findByTitleContainingIgnoreCaseAndActiveTrueAndStatus(title, PostStatus.PUBLISHED, PageRequest.of(page, size))
                .map(postMapper::toResponse);
    }

    @Override
    public void toggleLike(Long postId, String username) {
        Post post = postRepository.findByIdAndActiveTrue(postId)
                .orElseThrow(() -> new RuntimeException("Post bulunamadı"));

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        if (currentUser.getLikedPosts().contains(post)) {
            currentUser.getLikedPosts().remove(post);
            post.setLikeCount(post.getLikeCount() - 1);
        } else {
            currentUser.getLikedPosts().add(post);
            post.setLikeCount(post.getLikeCount() + 1);
        }

        userRepository.save(currentUser);
        postRepository.save(post);
    }

    @Override
    public List<UserResponse> getPostLikers(Long postId, String username) {
        Post post = postRepository.findByIdAndActiveTrue(postId)
                .orElseThrow(() -> new RuntimeException("Post bulunamadı"));

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        boolean isAuthor = post.getAuthor().getUsername().equals(username);
        boolean isAdmin = currentUser.getRole().name().equals("ROLE_ADMIN");

        if (!isAuthor && !isAdmin) {
            throw new RuntimeException("Bu postun beğenenlerini görme yetkiniz yok!");
        }

        return userMapper.toResponseList(new ArrayList<>(post.getLikedByUsers()));
    }

    @Override
    public void toggleBookmark(Long postId, String username) {
        Post post = postRepository.findByIdAndActiveTrue(postId)
                .orElseThrow(() -> new RuntimeException("Post bulunamadı"));

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        if (currentUser.getSavedPosts().contains(post)) {
            currentUser.getSavedPosts().remove(post);
        } else {
            currentUser.getSavedPosts().add(post);
        }

        userRepository.save(currentUser);
    }

    @Override
    public List<PostResponse> getMyBookmarkedPosts(String username) {
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        return postMapper.toResponseList(new ArrayList<>(currentUser.getSavedPosts()));
    }
}