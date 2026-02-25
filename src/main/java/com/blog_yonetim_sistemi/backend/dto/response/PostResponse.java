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
    private LocalDateTime createdDate;
}