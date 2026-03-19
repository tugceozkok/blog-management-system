package com.blog_yonetim_sistemi.backend.controller;

import com.blog_yonetim_sistemi.backend.dto.request.UserRequest;
import com.blog_yonetim_sistemi.backend.dto.response.UserResponse;
import com.blog_yonetim_sistemi.backend.entity.Role;
import com.blog_yonetim_sistemi.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Kullanıcı kayıt
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody UserRequest request) {

        UserResponse response = userService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Tüm kullanıcıları listeleme
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        List<UserResponse> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }

    // Kullanıcı rolünü güncelleme (SADECE ADMİNLER İÇİN)
    @PutMapping("/{id}/role")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // GÜNCELLENDİ: Tam isimle eşleşme garantisi!
    public ResponseEntity<UserResponse> updateUserRole(
            @PathVariable Long id,
            @RequestParam Role role) {

        UserResponse response = userService.updateUserRole(id, role);
        return ResponseEntity.ok(response);
    }
}