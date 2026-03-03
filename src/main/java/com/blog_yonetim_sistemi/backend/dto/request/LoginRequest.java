package com.blog_yonetim_sistemi.backend.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}