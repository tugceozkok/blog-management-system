package com.blog_yonetim_sistemi.backend.repository;

import com.blog_yonetim_sistemi.backend.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByActiveTrue(Pageable pageable);

    Optional<Post> findByIdAndActiveTrue(Long id);

    Page<Post> findByCategoryIdAndActiveTrue(Long categoryId, Pageable pageable);

    Page<Post> findByTagsIdAndActiveTrue(Long tagId, Pageable pageable);

    Page<Post> findByTitleContainingIgnoreCaseAndActiveTrue(String title, Pageable pageable);

    boolean existsByTitleAndActiveTrue(String title);
}