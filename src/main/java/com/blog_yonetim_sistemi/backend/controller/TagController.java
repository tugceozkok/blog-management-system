package com.blog_yonetim_sistemi.backend.controller;

import com.blog_yonetim_sistemi.backend.dto.request.TagRequest;
import com.blog_yonetim_sistemi.backend.dto.response.TagResponse;
import com.blog_yonetim_sistemi.backend.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // CORS hatasını önlemek için eklendi
@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<TagResponse> createTag(
            @Valid @RequestBody TagRequest request) {
        TagResponse response = tagService.createTag(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags() {
        List<TagResponse> tags = tagService.getAllTags();
        return ResponseEntity.ok(tags);
    }
}