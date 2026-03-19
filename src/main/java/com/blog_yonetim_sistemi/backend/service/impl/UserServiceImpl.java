package com.blog_yonetim_sistemi.backend.service.impl;

import com.blog_yonetim_sistemi.backend.dto.request.UserRequest;
import com.blog_yonetim_sistemi.backend.dto.response.UserResponse;
import com.blog_yonetim_sistemi.backend.entity.Role;
import com.blog_yonetim_sistemi.backend.entity.User;
import com.blog_yonetim_sistemi.backend.mapper.UserMapper;
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
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse register(UserRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username zaten mevcut");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email zaten mevcut");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Kayıt olan herkese standart kullanıcı rolü veriyoruz
        user.setRole(Role.ROLE_USER);

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toResponseList(users);
    }

    // YENİ: Rol güncelleme operasyonu
    @Override
    public UserResponse updateUserRole(Long id, Role newRole) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı!"));

        user.setRole(newRole);

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }
}