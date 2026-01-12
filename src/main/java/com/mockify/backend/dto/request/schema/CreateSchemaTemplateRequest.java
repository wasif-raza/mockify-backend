package com.mockify.backend.dto.request.schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateSchemaTemplateRequest {

    @NotNull
    private UUID templateId;

    @NotBlank
    private String schemaName;

    @NotBlank
    private String schemaSlug;
}

