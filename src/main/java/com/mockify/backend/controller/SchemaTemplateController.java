package com.mockify.backend.controller;

import com.mockify.backend.dto.request.schema.CreateSchemaTemplateRequest;
import com.mockify.backend.dto.response.schema.SchemaTemplateResponse;
import com.mockify.backend.service.SchemaTemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/templates")
@RequiredArgsConstructor
public class SchemaTemplateController {

    private final SchemaTemplateService templateService;

    // 1️⃣ List templates by category
    @GetMapping
    public ResponseEntity<List<SchemaTemplateResponse>> getTemplates() {
        return ResponseEntity.ok(templateService.getTemplates());
    }


//TODO it will test in future
    // 2️⃣ Create schema from template
//    @PostMapping("/projects/{projectId}/create-schema")
//    public ResponseEntity<Map<String, Object>> createSchemaFromTemplate(
//            @PathVariable UUID projectId,
//            @RequestBody @Valid CreateSchemaTemplateRequest request
//    ) {
//        UUID schemaId = templateService.createSchemaFromTemplate(projectId, request);
//
//        return ResponseEntity.ok(
//                Map.of("schemaId", schemaId)
//        );
//    }
}

