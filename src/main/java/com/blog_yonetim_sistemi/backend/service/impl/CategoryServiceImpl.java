package com.blog_yonetim_sistemi.backend.service.impl;

import com.blog_yonetim_sistemi.backend.entity.Category;
import com.blog_yonetim_sistemi.backend.repository.CategoryRepository;
import com.blog_yonetim_sistemi.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {

        if (categoryRepository.existsByName(category.getName())) {
            throw new RuntimeException("Kategori zaten mevcut");
        }

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}