package com.mockify.backend.dto.response.dashboard;

public record RecordHealthStats(
        long activeRecords,
        long expiredRecords,
        long expiringSoonRecords
) {}