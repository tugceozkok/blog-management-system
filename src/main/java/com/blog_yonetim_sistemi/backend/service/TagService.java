package com.blog_yonetim_sistemi.backend.service;

import com.blog_yonetim_sistemi.backend.dto.request.TagRequest;
import com.blog_yonetim_sistemi.backend.dto.response.TagResponse;

import java.util.List;

public interface TagService {

    TagResponse createTag(TagRequest request);

    List<TagResponse> getAllTags();
}