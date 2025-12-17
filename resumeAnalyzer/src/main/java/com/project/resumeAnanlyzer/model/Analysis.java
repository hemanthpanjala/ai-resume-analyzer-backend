package com.project.resumeAnanlyzer.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "analyses")
public class Analysis {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID jobRoleId;

    @Column
    private UUID userId;

    @Column(nullable = false)
    private String jobRoleTitle;

    @Column(nullable = false)
    private int matchScore;

    @Column(columnDefinition = "TEXT")
    private String strengths;

    @Column(columnDefinition = "TEXT")
    private String weaknesses;

    @Column(columnDefinition = "TEXT")
    private String missingSkills;

    @Column(columnDefinition = "TEXT")
    private String improvementSuggestions;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    public Analysis() {}

    public UUID getId() { return id; }
    public UUID getJobRoleId() { return jobRoleId; }
    public void setJobRoleId(UUID jobRoleId) { this.jobRoleId = jobRoleId; }

    public String getJobRoleTitle() { return jobRoleTitle; }
    public void setJobRoleTitle(String jobRoleTitle) { this.jobRoleTitle = jobRoleTitle; }

    public int getMatchScore() { return matchScore; }
    public void setMatchScore(int matchScore) { this.matchScore = matchScore; }

    public String getStrengths() { return strengths; }
    public void setStrengths(String strengths) { this.strengths = strengths; }

    public String getWeaknesses() { return weaknesses; }
    public void setWeaknesses(String weaknesses) { this.weaknesses = weaknesses; }

    public String getMissingSkills() { return missingSkills; }
    public void setMissingSkills(String missingSkills) { this.missingSkills = missingSkills; }

    public String getImprovementSuggestions() { return improvementSuggestions; }
    public void setImprovementSuggestions(String improvementSuggestions) { this.improvementSuggestions = improvementSuggestions; }

    public Instant getCreatedAt() { return createdAt; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
}
