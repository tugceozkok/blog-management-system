package com.blog_yonetim_sistemi.backend.mapper;

import com.blog_yonetim_sistemi.backend.dto.request.TagRequest;
import com.blog_yonetim_sistemi.backend.dto.response.TagResponse;
import com.blog_yonetim_sistemi.backend.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    // Entity -> Response
    TagResponse toResponse(Tag tag);

    List<TagResponse> toResponseList(List<Tag> tags);

    // Request -> Entity (Create için)
    Tag toEntity(TagRequest request);

    // Update için mevcut entity'yi güncelle
    void updateEntityFromRequest(TagRequest request,
                                 @MappingTarget Tag tag);
}