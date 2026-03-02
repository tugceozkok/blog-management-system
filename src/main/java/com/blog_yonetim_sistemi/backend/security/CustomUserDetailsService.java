package com.blog_yonetim_sistemi.backend.security;

import com.blog_yonetim_sistemi.backend.entity.User;
import com.blog_yonetim_sistemi.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        // Database'den kullanıcıyı bul
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Kullanıcı bulunamadı"));

        // Spring Security'nin anlayacağı User nesnesine çevir
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword()) // encoded password
                .authorities("USER") // şimdilik sabit rol
                .build();
    }
}