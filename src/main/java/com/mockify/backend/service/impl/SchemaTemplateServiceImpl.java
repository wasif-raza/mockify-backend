package com.mockify.backend.service.impl;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mockify.backend.dto.request.schema.CreateSchemaTemplateRequest;
import com.mockify.backend.dto.response.schema.SchemaTemplateResponse;
import com.mockify.backend.model.MockSchema;
import com.mockify.backend.model.Project;
import com.mockify.backend.model.SchemaTemplate;
import com.mockify.backend.repository.MockSchemaRepository;
import com.mockify.backend.repository.ProjectRepository;
import com.mockify.backend.repository.SchemaTemplateRepository;
import com.mockify.backend.service.SchemaTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SchemaTemplateServiceImpl implements SchemaTemplateService {

    private final SchemaTemplateRepository templateRepository;
    private final ProjectRepository projectRepository;
    private final MockSchemaRepository mockSchemaRepository;

    @Override
    public List<SchemaTemplateResponse> getTemplates() {

        List<SchemaTemplate> templates = templateRepository.findByIsSystemTrue();

        return templates.stream()
                .map(this::toResponse)
                .toList();
    }



    @Override
    public UUID createSchemaFromTemplate(UUID projectId, CreateSchemaTemplateRequest request) {

        SchemaTemplate template = templateRepository
                .findByIdAndIsSystemTrue(request.getTemplateId())
                .orElseThrow(() -> new RuntimeException("Template not found"));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        MockSchema schema = new MockSchema();
        schema.setName(request.getSchemaName());
        schema.setSlug(request.getSchemaSlug());
        schema.setProject(project);

        // Convert JSON string → Map
        schema.setSchemaJson(parseJson(template.getSchemaDefinition()));

        mockSchemaRepository.save(schema);

        return schema.getId();
    }

    private SchemaTemplateResponse toResponse(SchemaTemplate template) {
        return SchemaTemplateResponse.builder()
                .id(template.getId())
                .name(template.getName())
                .slug(template.getSlug())
                .description(template.getDescription())
                .category(template.getCategory())
                .schemaDefinition(template.getSchemaDefinition())
                .build();
    }

    private Map<String, Object> parseJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException("Invalid schema definition JSON");
        }
    }
}
