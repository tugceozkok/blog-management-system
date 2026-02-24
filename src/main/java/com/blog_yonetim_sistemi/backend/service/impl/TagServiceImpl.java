package com.blog_yonetim_sistemi.backend.service.impl;

import com.blog_yonetim_sistemi.backend.entity.Tag;
import com.blog_yonetim_sistemi.backend.repository.TagRepository;
import com.blog_yonetim_sistemi.backend.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public Tag createTag(Tag tag) {

        if (tagRepository.existsByName(tag.getName())) {
            throw new RuntimeException("Tag zaten mevcut");
        }

        return tagRepository.save(tag);
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }
}