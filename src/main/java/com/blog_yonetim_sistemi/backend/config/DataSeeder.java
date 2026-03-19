package com.blog_yonetim_sistemi.backend.config;

import com.blog_yonetim_sistemi.backend.entity.Role;
import com.blog_yonetim_sistemi.backend.entity.User;
import com.blog_yonetim_sistemi.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (!userRepository.existsByUsername("superadmin")) {
            User admin = new User();
            admin.setUsername("superadmin");
            admin.setEmail("admin@blog.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ROLE_ADMIN);

            userRepository.save(admin);
            System.out.println("Uyarı: Veritabanında Admin bulunamadı, varsayılan 'superadmin' hesabı otomatik oluşturuldu!");
        }
    }
}