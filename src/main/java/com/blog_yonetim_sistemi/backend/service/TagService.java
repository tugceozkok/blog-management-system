package com.blog_yonetim_sistemi.backend.service;

import com.blog_yonetim_sistemi.backend.entity.Tag;

import java.util.List;

public interface TagService {

    Tag createTag(Tag tag);

    List<Tag> getAllTags();
}