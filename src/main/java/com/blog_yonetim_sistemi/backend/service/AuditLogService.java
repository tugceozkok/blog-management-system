package com.blog_yonetim_sistemi.backend.service;

public interface AuditLogService {

    void logAction(String username, String action, String detail);
}