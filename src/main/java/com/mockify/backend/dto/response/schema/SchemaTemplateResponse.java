package com.mockify.backend.dto.response.schema;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SchemaTemplateResponse {
    private UUID id;
    private String name;
    private String slug;
    private String description;
    private String category;
    private String schemaDefinition;
}
