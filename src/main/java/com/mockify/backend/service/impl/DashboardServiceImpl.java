package com.mockify.backend.service.impl;

import com.mockify.backend.dto.response.dashboard.*;
import com.mockify.backend.repository.DashboardRepository;
import com.mockify.backend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepository dashboardRepository;

    @Override
    public UserStats userStats(UUID userId) {
        return dashboardRepository.userStats(userId);
    }

    @Override
    public OrganizationStats organizationStats(UUID orgId) {
        return dashboardRepository.organizationStats(orgId);
    }

    @Override
    public ProjectStats projectStats(UUID projectId) {
        return dashboardRepository.projectStats(projectId);
    }

    @Override
    public SchemaStats schemaStats(UUID schemaId) {
        return dashboardRepository.schemaStats(schemaId);
    }

    @Override
    public RecordHealthStats recordHealth(UUID userId) {
        LocalDateTime now = LocalDateTime.now();
        return dashboardRepository.recordHealthStats(userId, now, now.plusMinutes(30));
    }
}
