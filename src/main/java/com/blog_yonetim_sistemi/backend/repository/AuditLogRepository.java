package com.blog_yonetim_sistemi.backend.repository;

import com.blog_yonetim_sistemi.backend.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    // Tüm logları en yeniden en eskiye doğru listele
    Page<AuditLog> findAllByOrderByTimestampDesc(Pageable pageable);
}