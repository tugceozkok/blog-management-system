package com.blog_yonetim_sistemi.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private String username;
    private String role; // Frontend'in RBAC (Rol Kontrolü) yapabilmesi için eklendi
}