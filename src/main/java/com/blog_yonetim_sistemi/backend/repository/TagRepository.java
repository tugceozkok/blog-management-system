package com.blog_yonetim_sistemi.backend.repository;

import com.blog_yonetim_sistemi.backend.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);

    boolean existsByName(String name);
}