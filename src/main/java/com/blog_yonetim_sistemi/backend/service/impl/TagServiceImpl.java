package com.blog_yonetim_sistemi.backend.service.impl;

import com.blog_yonetim_sistemi.backend.dto.request.TagRequest;
import com.blog_yonetim_sistemi.backend.dto.response.TagResponse;
import com.blog_yonetim_sistemi.backend.entity.Tag;
import com.blog_yonetim_sistemi.backend.mapper.TagMapper;
import com.blog_yonetim_sistemi.backend.repository.TagRepository;
import com.blog_yonetim_sistemi.backend.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public TagResponse createTag(TagRequest request) {

        if (tagRepository.existsByName(request.getName())) {
            throw new RuntimeException("Tag zaten mevcut");
        }

        Tag tag = tagMapper.toEntity(request);

        Tag savedTag = tagRepository.save(tag);

        return tagMapper.toResponse(savedTag);
    }

    @Override
    public List<TagResponse> getAllTags() {

        List<Tag> tags = tagRepository.findAll();

        return tagMapper.toResponseList(tags);
    }
}