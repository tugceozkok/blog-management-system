package com.blog_yonetim_sistemi.backend.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AuditLogResponse {

    private Long id;
    private String username;
    private String action;
    private String detail;
    private LocalDateTime timestamp;
}