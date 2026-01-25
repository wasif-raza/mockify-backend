package com.mockify.backend.dto.response.dashboard;

public record ProjectStats(
        long schemas,
        long records
) {}