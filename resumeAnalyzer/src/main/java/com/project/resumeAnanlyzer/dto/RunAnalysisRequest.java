package com.project.resumeAnanlyzer.dto;

import java.util.List;
import java.util.UUID;

public class RunAnalysisRequest {

    private UUID jobRoleId;
    private String title;
    private String description;
    private List<String> requiredSkills;
    private String resumeText;

    public UUID getJobRoleId() { return jobRoleId; }
    public void setJobRoleId(UUID jobRoleId) { this.jobRoleId = jobRoleId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getRequiredSkills() { return requiredSkills; }
    public void setRequiredSkills(List<String> requiredSkills) { this.requiredSkills = requiredSkills; }

    public String getResumeText() { return resumeText; }
    public void setResumeText(String resumeText) { this.resumeText = resumeText; }
}