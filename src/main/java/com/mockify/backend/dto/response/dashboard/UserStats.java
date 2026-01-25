package com.mockify.backend.dto.response.dashboard;

public record UserStats(
        long organizations,
        long projects,
        long schemas,
        long records
) {}
