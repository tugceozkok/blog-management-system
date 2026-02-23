package com.blog_yonetim_sistemi.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data // Getter, Setter, toString gibi metotları otomatik oluşturur
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //

    @Column(unique = true, nullable = false)
    private String username; //

    @Column(nullable = false)
    private String email; // [cite: 19]

    @Column(nullable = false)
    private String password; // [cite: 20] (Hashlenmiş saklanacak)

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate; //
}