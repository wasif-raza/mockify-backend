package com.mockify.backend.repository;


import com.mockify.backend.model.SchemaTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SchemaTemplateRepository extends JpaRepository<SchemaTemplate, UUID> {

    List<SchemaTemplate> findByCategoryAndIsSystemTrue(String category);

    List<SchemaTemplate> findByIsSystemTrue();

    Optional<SchemaTemplate> findByIdAndIsSystemTrue(UUID id);
}

