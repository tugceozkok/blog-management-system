package com.blog_yonetim_sistemi.backend.repository;

import com.blog_yonetim_sistemi.backend.entity.Post;
import com.blog_yonetim_sistemi.backend.entity.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 1. ZİYARETÇİ / ANA SAYFA İÇİN (Sadece PUBLISHED olanlar)
    Page<Post> findByActiveTrueAndStatus(PostStatus status, Pageable pageable);

    Optional<Post> findByIdAndActiveTrueAndStatus(Long id, PostStatus status);

    // (Ana sayfada kategori/tag filtreleme aramaları - Sadece PUBLISHED olanlar)
    Page<Post> findByCategoryIdAndActiveTrueAndStatus(Long categoryId, PostStatus status, Pageable pageable);
    Page<Post> findByTagsIdAndActiveTrueAndStatus(Long tagId, PostStatus status, Pageable pageable);
    Page<Post> findByTitleContainingIgnoreCaseAndActiveTrueAndStatus(String title, PostStatus status, Pageable pageable);

    // ========================================================================

    // 2. YAZARIN KENDİ PANELİ İÇİN (Draft, Pending, Published hepsi)
    // Yazar kendi profiline girdiğinde sadece kendi ID'sine ait olanları çekeriz.
    Page<Post> findByAuthorIdAndActiveTrue(Long authorId, Pageable pageable);

    Optional<Post> findByIdAndAuthorIdAndActiveTrue(Long id, Long authorId);

    // ========================================================================

    // 3. EDİTÖR VE ADMİN PANELİ İÇİN (Taslakları GİZLE)
    // "In" kelimesi bir liste alır. Service katmanında buraya sadece [PENDING, PUBLISHED] göndereceğiz.
    // Böylece DRAFT olanlar asla Editörün ekranına düşmeyecek!
    Page<Post> findByActiveTrueAndStatusIn(Collection<PostStatus> statuses, Pageable pageable);

    Optional<Post> findByIdAndActiveTrueAndStatusIn(Long id, Collection<PostStatus> statuses);

}