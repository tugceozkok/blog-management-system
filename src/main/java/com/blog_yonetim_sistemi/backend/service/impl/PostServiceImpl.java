package com.blog_yonetim_sistemi.backend.service.impl;

import com.blog_yonetim_sistemi.backend.dto.request.PostRequest;
import com.blog_yonetim_sistemi.backend.dto.response.PostResponse;
import com.blog_yonetim_sistemi.backend.entity.Post;
import com.blog_yonetim_sistemi.backend.entity.User; // Eksik olabilir
import com.blog_yonetim_sistemi.backend.mapper.PostMapper;
import com.blog_yonetim_sistemi.backend.repository.*; // Tüm repolar için
import com.blog_yonetim_sistemi.backend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest; // KRİTİK: PageRequest için şart
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Veri güvenliği için

import java.util.List;
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

        if (postRepository.existsByTitleAndActiveTrue(request.getTitle())) {
            throw new RuntimeException("Bu başlıkta aktif post zaten var");
        }

        if (request.getTagIds() == null || request.getTagIds().isEmpty()) {
            throw new RuntimeException("Post en az 1 tag içermelidir");
        }

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthor(user);
        post.setActive(true);

        post.setCategory(
                categoryRepository.findById(request.getCategoryId())
                        .orElseThrow(() -> new RuntimeException("Kategori bulunamadı"))
        );

        post.setTags(
                tagRepository.findAllById(request.getTagIds())
        );

        return postMapper.toResponse(postRepository.save(post));
    }

    @Override
    public PostResponse updatePost(Long id, PostRequest request, String username) {

        Post existingPost = postRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new RuntimeException("Post bulunamadı"));

        if (!existingPost.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("Bu postu güncelleme yetkiniz yok");
        }

        existingPost.setTitle(request.getTitle());
        existingPost.setContent(request.getContent());

        existingPost.setCategory(
                categoryRepository.findById(request.getCategoryId())
                        .orElseThrow(() -> new RuntimeException("Kategori bulunamadı"))
        );

        existingPost.setTags(
                tagRepository.findAllById(request.getTagIds())
        );

        return postMapper.toResponse(postRepository.save(existingPost));
    }

    @Override
    public void deletePost(Long id, String username) {

        Post post = postRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new RuntimeException("Post bulunamadı"));

        if (!post.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("Bu postu silme yetkiniz yok");
        }

        post.setActive(false);
        postRepository.save(post);
    }

    @Override
    public PostResponse getPostById(Long id) {
        return postMapper.toResponse(
                postRepository.findByIdAndActiveTrue(id)
                        .orElseThrow(() -> new RuntimeException("Post bulunamadı"))
        );
    }

    @Override
    public Page<PostResponse> getAllActivePosts(int page, int size) {
        return postRepository.findByActiveTrue(PageRequest.of(page, size))
                .map(postMapper::toResponse);
    }

    @Override
    public Page<PostResponse> getPostsByCategory(Long categoryId, int page, int size) {
        return postRepository.findByCategoryIdAndActiveTrue(categoryId, PageRequest.of(page, size))
                .map(postMapper::toResponse);
    }

    @Override
    public Page<PostResponse> getPostsByTag(Long tagId, int page, int size) {
        return postRepository.findByTagsIdAndActiveTrue(tagId, PageRequest.of(page, size))
                .map(postMapper::toResponse);
    }

    @Override
    public Page<PostResponse> searchByTitle(String title, int page, int size) {
        return postRepository
                .findByTitleContainingIgnoreCaseAndActiveTrue(title, PageRequest.of(page, size))
                .map(postMapper::toResponse);
    }
}