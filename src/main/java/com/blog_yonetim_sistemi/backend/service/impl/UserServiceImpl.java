package com.blog_yonetim_sistemi.backend.service.impl;

import com.blog_yonetim_sistemi.backend.entity.User;
import com.blog_yonetim_sistemi.backend.repository.UserRepository;
import com.blog_yonetim_sistemi.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username zaten mevcut");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email zaten mevcut");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}