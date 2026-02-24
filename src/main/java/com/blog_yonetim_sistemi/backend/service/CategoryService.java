package com.blog_yonetim_sistemi.backend.service;

import com.blog_yonetim_sistemi.backend.entity.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(Category category);

    List<Category> getAllCategories();
}