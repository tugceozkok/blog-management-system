package com.blog_yonetim_sistemi.backend.service;

import com.blog_yonetim_sistemi.backend.entity.User;

import java.util.List;

public interface UserService {

    User register(User user);

    List<User> getAllUsers();
}