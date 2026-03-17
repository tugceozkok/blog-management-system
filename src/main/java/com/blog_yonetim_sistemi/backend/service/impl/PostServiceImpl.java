package com.blog_yonetim_sistemi.backend.service.impl;

import com.blog_yonetim_sistemi.backend.dto.request.PostRequest;
import com.blog_yonetim_sistemi.backend.dto.response.PostResponse;
import com.blog_yonetim_sistemi.backend.entity.Post;
import com.blog_yonetim_sistemi.backend.entity.PostStatus;
import com.blog_yonetim_sistemi.backend.entity.User;
import com.blog_yonetim_sistemi.backend.mapper.PostMapper;
import com.blog_yonetim_sistemi.backend.repository.*;
import com.blog_yonetim_sistemi.backend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final PostMapper postMapper;

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

        // Gelen isteğe göre statüyü belirliyoruz (React'tan butonla gelecek)
        if (request.getStatus() != null && request.getStatus().equalsIgnoreCase("PENDING")) {
            post.setStatus(PostStatus.PENDING);
        } else {
            post.setStatus(PostStatus.DRAFT);
        }

        post.setCategory(categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı")));

        // List olan tag'leri Set'e çeviriyoruz
        post.setTags(new HashSet<>(tagRepository.findAllById(request.getTagIds())));

        return postMapper.toResponse(postRepository.save(post));
    }

    @Override
    public PostResponse updatePost(Long id, PostRequest request, String username) {

        Post existingPost = postRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new RuntimeException("Post bulunamadı"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        // Yetki Kontrolü: Yazar, Editör veya Admin mi?
        boolean isAuthor = existingPost.getAuthor().getUsername().equals(username);
        boolean isEditor = user.getRole().name().equals("ROLE_EDITOR");
        boolean isAdmin = user.getRole().name().equals("ROLE_ADMIN");

        if (!isAuthor && !isEditor && !isAdmin) {
            throw new RuntimeException("Bu postu güncelleme yetkiniz yok");
        }

        existingPost.setTitle(request.getTitle());
        existingPost.setContent(request.getContent());

        // Eğer Editör onay verip statüyü PUBLISHED yapıyorsa bunu da güncelliyoruz
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

        // Yetki Kontrolü: Yazar, Editör veya Admin mi?
        boolean isAuthor = post.getAuthor().getUsername().equals(username);
        boolean isEditor = user.getRole().name().equals("ROLE_EDITOR");
        boolean isAdmin = user.getRole().name().equals("ROLE_ADMIN");

        if (!isAuthor && !isEditor && !isAdmin) {
            throw new RuntimeException("Bu postu silme yetkiniz yok");
        }

        post.setActive(false); // Soft Delete (Veritabanından silmez, gizler)
        postRepository.save(post);
    }

    @Override
    public PostResponse getPostById(Long id) {
        // Ziyaretçiler için sadece Yayında (PUBLISHED) olanları getirir
        return postMapper.toResponse(
                postRepository.findByIdAndActiveTrueAndStatus(id, PostStatus.PUBLISHED)
                        .orElseThrow(() -> new RuntimeException("Post bulunamadı veya yayında değil"))
        );
    }

    @Override
    public Page<PostResponse> getAllActivePosts(int page, int size) {
        // Ana sayfa akışı: Sadece PUBLISHED (Yayında) olanlar
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
}