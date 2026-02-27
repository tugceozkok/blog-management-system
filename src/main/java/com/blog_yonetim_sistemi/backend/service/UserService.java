package com.blog_yonetim_sistemi.backend.service;

import com.blog_yonetim_sistemi.backend.dto.request.UserRequest;
import com.blog_yonetim_sistemi.backend.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse register(UserRequest request);

    List<UserResponse> getAllUsers();
}