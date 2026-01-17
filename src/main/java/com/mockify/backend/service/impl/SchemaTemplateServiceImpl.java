package com.mockify.backend.service.impl;

import com.mockify.backend.dto.response.schema.MockSchemaResponse;
import com.mockify.backend.dto.response.schema.SchemaTemplateResponse;
import com.mockify.backend.exception.ResourceNotFoundException;
import com.mockify.backend.mapper.MockSchemaMapper;
import com.mockify.backend.mapper.SchemaTemplateMapper;
import com.mockify.backend.model.MockSchema;
import com.mockify.backend.model.Project;
import com.mockify.backend.model.SchemaTemplate;
import com.mockify.backend.repository.MockSchemaRepository;
import com.mockify.backend.repository.ProjectRepository;
import com.mockify.backend.repository.SchemaTemplateRepository;
import com.mockify.backend.service.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchemaTemplateServiceImpl implements SchemaTemplateService {

    private final SchemaTemplateRepository schemaTemplateRepository;
    private final ProjectRepository projectRepository;
    private final MockSchemaRepository mockSchemaRepository;

    private final SchemaTemplateMapper schemaTemplateMapper;
    private final MockSchemaMapper mockSchemaMapper;

    private final SlugService slugService;
    private final AccessControlService accessControlService;
    private final EndpointService endpointService;


    // 1. Get system templates

    @Override
    @Transactional(readOnly = true)

    public List<SchemaTemplateResponse> getSystemTemplates() {

        List<SchemaTemplate> templates =
                schemaTemplateRepository.findBySystemTemplateTrue();

        return schemaTemplateMapper.toResponseList(templates);
    }


    // 2. Apply template to project

    @Override
    @Transactional

    public MockSchemaResponse applyTemplateToProject(
            UUID userId,
            UUID projectId,
            String templateSlug
    ) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        // Security boundary:
        // Even if the user knows the projectId, they must belong to the same organization.
        accessControlService.checkOrganizationAccess(
                userId,
                project.getOrganization(),
                "Project"
        );

        SchemaTemplate template =
                schemaTemplateRepository.findBySlugAndSystemTemplateTrue(templateSlug)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Schema template not found")
                        );

        // Start with a human-readable slug based on template name
        String schemaSlug = slugService.generateSlug(template.getName());

        // Slugs must be unique *within a project* to avoid URL / endpoint conflicts.
        if (mockSchemaRepository.existsBySlugAndProjectId(schemaSlug, projectId)) {
            schemaSlug = slugService.generateUniqueSlug(schemaSlug);
        }

        MockSchema schema = new MockSchema();
        schema.setName(template.getName());
        schema.setSlug(schemaSlug);


        schema.setSchemaJson(new HashMap<>(template.getSchemaJson()));

        schema.setProject(project);

        MockSchema savedSchema = mockSchemaRepository.save(schema);


        endpointService.createEndpoint(savedSchema);

        return mockSchemaMapper.toResponse(savedSchema);
    }
}
