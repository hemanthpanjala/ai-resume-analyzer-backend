package com.project.resumeAnanlyzer.dto;

import com.project.resumeAnanlyzer.model.Analysis;

import java.time.Instant;
import java.util.UUID;

public class AnalysisSummaryResponse {
    private UUID id;
    private String jobRoleTitle;
    private int matchScore;
    private Instant createdAt;

    public AnalysisSummaryResponse() {}

    public AnalysisSummaryResponse(UUID id, String jobRoleTitle, int matchScore, Instant createdAt) {
        this.id = id;
        this.jobRoleTitle = jobRoleTitle;
        this.matchScore = matchScore;
        this.createdAt = createdAt;
    }

    public static AnalysisSummaryResponse from(Analysis a) {
        return new AnalysisSummaryResponse(
                a.getId(),
                a.getJobRoleTitle(),
                a.getMatchScore(),
                a.getCreatedAt()
        );
    }

    public UUID getId() { return id; }
    public String getJobRoleTitle() { return jobRoleTitle; }
    public int getMatchScore() { return matchScore; }
    public Instant getCreatedAt() { return createdAt; }

    public void setId(UUID id) { this.id = id; }
    public void setJobRoleTitle(String jobRoleTitle) { this.jobRoleTitle = jobRoleTitle; }
    public void setMatchScore(int matchScore) { this.matchScore = matchScore; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
