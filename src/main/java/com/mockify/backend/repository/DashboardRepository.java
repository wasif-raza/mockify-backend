package com.mockify.backend.repository;

import com.mockify.backend.dto.response.dashboard.*;
import com.mockify.backend.model.Organization;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface DashboardRepository extends org.springframework.data.repository.Repository<Organization, UUID> {

    /*
       USER LEVEL STATS
       organizations, projects, schemas, records under a user
    */
    @Query("""
        SELECT new com.mockify.backend.dto.response.dashboard.UserStats(
            COUNT(DISTINCT o.id),
            COUNT(DISTINCT p.id),
            COUNT(DISTINCT s.id),
            COUNT(DISTINCT r.id)
        )
        FROM Organization o
        LEFT JOIN o.projects p
        LEFT JOIN p.mockSchemas s
        LEFT JOIN s.mockRecords r
        WHERE o.owner.id = :userId
    """)
    UserStats userStats(@Param("userId") UUID userId);


    /*
       ORGANIZATION LEVEL STATS
       projects, schemas, records under an org
    */
    @Query("""
        SELECT new com.mockify.backend.dto.response.dashboard.OrganizationStats(
            COUNT(DISTINCT p.id),
            COUNT(DISTINCT s.id),
            COUNT(DISTINCT r.id)
        )
        FROM Project p
        LEFT JOIN p.mockSchemas s
        LEFT JOIN s.mockRecords r
        WHERE p.organization.id = :orgId
    """)
    OrganizationStats organizationStats(@Param("orgId") UUID orgId);


    /*
       PROJECT LEVEL STATS
       schemas, records under a project
    */
    @Query("""
        SELECT new com.mockify.backend.dto.response.dashboard.ProjectStats(
            COUNT(DISTINCT s.id),
            COUNT(DISTINCT r.id)
        )
        FROM MockSchema s
        LEFT JOIN s.mockRecords r
        WHERE s.project.id = :projectId
    """)
    ProjectStats projectStats(@Param("projectId") UUID projectId);


    /*
       SCHEMA LEVEL STATS
       records under a schema
    */
    @Query("""
        SELECT new com.mockify.backend.dto.response.dashboard.SchemaStats(
            COUNT(r.id)
        )
        FROM MockRecord r
        WHERE r.mockSchema.id = :schemaId
    """)
    SchemaStats schemaStats(@Param("schemaId") UUID schemaId);


    /*
       RECORD HEALTH STATS (USER LEVEL)
       active, expired, expiring soon
    */
    @Query("""
        SELECT new com.mockify.backend.dto.response.dashboard.RecordHealthStats(
            COUNT(CASE
                WHEN r.expiresAt > :now THEN 1
            END),
            COUNT(CASE
                WHEN r.expiresAt <= :now THEN 1
            END),
            COUNT(CASE
                WHEN r.expiresAt > :now AND r.expiresAt <= :soon THEN 1
            END)
        )
        FROM MockRecord r
        WHERE r.mockSchema.project.organization.owner.id = :userId
    """)
    RecordHealthStats recordHealthStats(
            @Param("userId") UUID userId,
            @Param("now") LocalDateTime now,
            @Param("soon") LocalDateTime soon
    );
}