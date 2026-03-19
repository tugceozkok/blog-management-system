package com.blog_yonetim_sistemi.backend.service;

import com.blog_yonetim_sistemi.backend.dto.request.UserRequest;
import com.blog_yonetim_sistemi.backend.dto.response.UserResponse;
import com.blog_yonetim_sistemi.backend.entity.Role;

import java.util.List;

public interface UserService {

    UserResponse register(UserRequest request);

    List<UserResponse> getAllUsers();

    // yetki güncelleme metodu
    UserResponse updateUserRole(Long id, Role newRole);
}