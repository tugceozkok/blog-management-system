package com.blog_yonetim_sistemi.backend.service;

import com.blog_yonetim_sistemi.backend.dto.request.CategoryRequest;
import com.blog_yonetim_sistemi.backend.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);

    List<CategoryResponse> getAllCategories();
}