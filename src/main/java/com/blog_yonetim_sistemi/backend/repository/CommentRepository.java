package com.blog_yonetim_sistemi.backend.repository;

import com.blog_yonetim_sistemi.backend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Bir postun altındaki yorumları en yeniden eskiye doğru getir
    List<Comment> findByPostIdOrderByCreatedDateDesc(Long postId);
}