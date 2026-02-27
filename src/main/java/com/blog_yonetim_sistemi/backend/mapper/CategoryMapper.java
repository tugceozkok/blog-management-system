package com.blog_yonetim_sistemi.backend.mapper;

import com.blog_yonetim_sistemi.backend.dto.request.CategoryRequest;
import com.blog_yonetim_sistemi.backend.dto.response.CategoryResponse;
import com.blog_yonetim_sistemi.backend.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    // Entity -> Response
    CategoryResponse toResponse(Category category);

    List<CategoryResponse> toResponseList(List<Category> categories);

    // Request -> Entity (Create için)
    Category toEntity(CategoryRequest request);

    // Update için mevcut entity'yi güncelle
    void updateEntityFromRequest(CategoryRequest request,
                                 @MappingTarget Category category);
}