package com.mockify.backend.dto.response.dashboard;

public record OrganizationStats(
        long projects,
        long schemas,
        long records
) {}