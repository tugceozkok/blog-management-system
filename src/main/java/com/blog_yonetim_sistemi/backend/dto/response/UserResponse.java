package com.blog_yonetim_sistemi.backend.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdDate;
}