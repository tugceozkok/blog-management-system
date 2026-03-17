package com.blog_yonetim_sistemi.backend.controller;

import com.blog_yonetim_sistemi.backend.dto.request.LoginRequest;
import com.blog_yonetim_sistemi.backend.dto.response.LoginResponse;
import com.blog_yonetim_sistemi.backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        // 1. Kullanıcı adı ve şifreyi doğrula
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // 2. Doğrulanan kullanıcının detaylarını al
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 3. Token üret
        String token = jwtUtil.generateToken(userDetails);

        // 4. Kullanıcının adını ve rolünü al
        String username = userDetails.getUsername();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        // 5. YENİ: React'a Token, Username ve Role bilgilerinin üçünü de gönder!
        return new LoginResponse(token, username, role);
    }
}