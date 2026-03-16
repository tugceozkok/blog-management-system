package com.blog_yonetim_sistemi.backend.dto.response;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private String authorUsername;
    private String categoryName;
    private List<String> tags;

    // YENİ EKLENENLER:
    private String status; // DRAFT, PENDING, PUBLISHED (Ekranda rozet olarak göstermek için)
    private boolean active; // Admin panelinde silinmişleri ayırt etmek için
    private int likeCount; // Kaç kişi beğendi?
    private int commentCount; // Kaç yorum var?

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}