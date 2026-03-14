package com.blog_yonetim_sistemi.backend.entity;

public enum PostStatus {
    DRAFT,      // Yazarın üzerinde çalıştığı, henüz onaya gitmemiş taslak
    PENDING,    // Editör onayı bekleyen yazı
    PUBLISHED   // Editör tarafından onaylanmış ve herkesin akışına düşen aktif yazı
}