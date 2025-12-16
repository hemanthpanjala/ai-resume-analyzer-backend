package com.project.resumeAnanlyzer.dto;

import java.util.List;
import java.util.UUID;

public class JobRoleResponse {

    private UUID id;
    private String title;
    private String description;
    private List<String> requiredSkills;

    public JobRoleResponse(UUID id, String title, String description, List<String> requiredSkills) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.requiredSkills = requiredSkills;
    }

    public JobRoleResponse() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(List<String> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }
}
