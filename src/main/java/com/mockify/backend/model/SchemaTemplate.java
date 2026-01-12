package com.mockify.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "schema_templates",
        indexes = {
                @Index(name = "idx_schema_templates_slug", columnList = "slug"),
                @Index(name = "idx_schema_templates_category", columnList = "category")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchemaTemplate {

    @Id
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, nullable = false, unique = true)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 30, nullable = false)
    private String category;

    @Column(columnDefinition = "jsonb", nullable = false)
    private String schemaDefinition;

    @Column(nullable = false)
    private Boolean isSystem = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Auto timestamps
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

