package com.project.resumeAnanlyzer.controller;

import com.project.resumeAnanlyzer.dto.AnalysisSummaryResponse;
import com.project.resumeAnanlyzer.repository.AnalysisRepository;
import com.project.resumeAnanlyzer.repository.JobRoleRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.project.resumeAnanlyzer.model.Analysis;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    private final AnalysisRepository analysisRepo;
    private final JobRoleRepository jobRoleRepo;

    public DashboardController(AnalysisRepository analysisRepo, JobRoleRepository jobRoleRepo) {
        this.analysisRepo = analysisRepo;
        this.jobRoleRepo = jobRoleRepo;
    }

    private UUID currentUserId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal() == null) {
            throw new IllegalStateException("No authenticated user found");
        }
        return UUID.fromString(auth.getPrincipal().toString());
    }

    @GetMapping
    public Map<String, Object> get() {
        UUID userId = currentUserId();

        long totalJobRoles = jobRoleRepo.count();
        long totalAnalyses = analysisRepo.countByUserId(userId);

        var recent = analysisRepo.findTop3ByUserIdOrderByCreatedAtDesc(userId);
        int avg = 0;

        if (totalAnalyses > 0) {
            int sum = analysisRepo.findByUserIdOrderByCreatedAtDesc(userId)
                    .stream()
                    .mapToInt(Analysis::getMatchScore)
                    .sum();
            avg = Math.round((float) sum / (float) totalAnalyses);
        }

        var recentDto = recent.stream()
                .map(AnalysisSummaryResponse::from)
                .toList();

        return Map.of(
                "totalJobRoles", totalJobRoles,
                "totalAnalyses", totalAnalyses,
                "avgMatchScore", avg,
                "recentAnalyses", recentDto
        );
    }
}
