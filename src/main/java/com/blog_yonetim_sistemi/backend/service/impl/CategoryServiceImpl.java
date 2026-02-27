package com.blog_yonetim_sistemi.backend.service.impl;

import com.blog_yonetim_sistemi.backend.dto.request.CategoryRequest;
import com.blog_yonetim_sistemi.backend.dto.response.CategoryResponse;
import com.blog_yonetim_sistemi.backend.entity.Category;
import com.blog_yonetim_sistemi.backend.mapper.CategoryMapper;
import com.blog_yonetim_sistemi.backend.repository.CategoryRepository;
import com.blog_yonetim_sistemi.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {

        if (categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Kategori zaten mevcut");
        }

        Category category = categoryMapper.toEntity(request);

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();

        return categoryMapper.toResponseList(categories);
    }
}