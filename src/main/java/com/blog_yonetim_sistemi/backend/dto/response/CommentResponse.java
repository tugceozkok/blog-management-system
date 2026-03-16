package com.blog_yonetim_sistemi.backend.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommentResponse {

    private Long id;
    private String content;
    private String authorUsername; // Yorumu yapanın adı
    private LocalDateTime createdDate;
}