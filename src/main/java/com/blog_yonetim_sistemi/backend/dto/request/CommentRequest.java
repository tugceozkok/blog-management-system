package com.blog_yonetim_sistemi.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentRequest {

    @NotBlank
    private String content;

    @NotNull
    private Long postId; // Yorum hangi posta yapılıyor?
}