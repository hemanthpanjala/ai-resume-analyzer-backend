package com.project.resumeAnanlyzer.dto;

import com.project.resumeAnanlyzer.model.Analysis;

import java.time.Instant;
import java.util.UUID;

public class AnalysisDetailResponse {
    private UUID id;
    private String jobRoleTitle;
    private int matchScore;
    private Instant createdAt;

    private String strengths;
    private String weaknesses;
    private String missingSkills;
    private String improvementSuggestions;

    public AnalysisDetailResponse() {}

    public AnalysisDetailResponse(UUID id, String jobRoleTitle, int matchScore, Instant createdAt,
                                  String strengths, String weaknesses, String missingSkills, String improvementSuggestions) {
        this.id = id;
        this.jobRoleTitle = jobRoleTitle;
        this.matchScore = matchScore;
        this.createdAt = createdAt;
        this.strengths = strengths;
        this.weaknesses = weaknesses;
        this.missingSkills = missingSkills;
        this.improvementSuggestions = improvementSuggestions;
    }

    public static AnalysisDetailResponse from(Analysis a) {
        return new AnalysisDetailResponse(
                a.getId(),
                a.getJobRoleTitle(),
                a.getMatchScore(),
                a.getCreatedAt(),
                a.getStrengths(),
                a.getWeaknesses(),
                a.getMissingSkills(),
                a.getImprovementSuggestions()
        );
    }

    public UUID getId() { return id; }
    public String getJobRoleTitle() { return jobRoleTitle; }
    public int getMatchScore() { return matchScore; }
    public Instant getCreatedAt() { return createdAt; }
    public String getStrengths() { return strengths; }
    public String getWeaknesses() { return weaknesses; }
    public String getMissingSkills() { return missingSkills; }
    public String getImprovementSuggestions() { return improvementSuggestions; }

    public void setId(UUID id) { this.id = id; }
    public void setJobRoleTitle(String jobRoleTitle) { this.jobRoleTitle = jobRoleTitle; }
    public void setMatchScore(int matchScore) { this.matchScore = matchScore; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public void setStrengths(String strengths) { this.strengths = strengths; }
    public void setWeaknesses(String weaknesses) { this.weaknesses = weaknesses; }
    public void setMissingSkills(String missingSkills) { this.missingSkills = missingSkills; }
    public void setImprovementSuggestions(String improvementSuggestions) { this.improvementSuggestions = improvementSuggestions; }
}
