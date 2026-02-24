package com.blog_yonetim_sistemi.backend.service.impl;

import com.blog_yonetim_sistemi.backend.entity.Post;
import com.blog_yonetim_sistemi.backend.entity.User;
import com.blog_yonetim_sistemi.backend.repository.PostRepository;
import com.blog_yonetim_sistemi.backend.repository.UserRepository;
import com.blog_yonetim_sistemi.backend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public Post createPost(Post post, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        if (postRepository.existsByTitleAndActiveTrue(post.getTitle())) {
            throw new RuntimeException("Bu başlıkta aktif post zaten var");
        }

        if (post.getTags() == null || post.getTags().isEmpty()) {
            throw new RuntimeException("Post en az 1 tag içermelidir");
        }

        post.setAuthor(user);
        post.setActive(true);

        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Long id, Post updatedPost, String username) {

        Post existingPost = postRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new RuntimeException("Post bulunamadı"));

        if (!existingPost.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("Bu postu güncelleme yetkiniz yok");
        }

        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setContent(updatedPost.getContent());
        existingPost.setCategory(updatedPost.getCategory());
        existingPost.setTags(updatedPost.getTags());

        return postRepository.save(existingPost);
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
    public Post getPostById(Long id) {
        return postRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new RuntimeException("Post bulunamadı"));
    }

    @Override
    public Page<Post> getAllActivePosts(int page, int size) {
        return postRepository.findByActiveTrue(PageRequest.of(page, size));
    }

    @Override
    public Page<Post> getPostsByCategory(Long categoryId, int page, int size) {
        return postRepository.findByCategoryIdAndActiveTrue(categoryId, PageRequest.of(page, size));
    }

    @Override
    public Page<Post> getPostsByTag(Long tagId, int page, int size) {
        return postRepository.findByTagsIdAndActiveTrue(tagId, PageRequest.of(page, size));
    }

    @Override
    public Page<Post> searchByTitle(String title, int page, int size) {
        return postRepository.findByTitleContainingIgnoreCaseAndActiveTrue(
                title, PageRequest.of(page, size));
    }
}