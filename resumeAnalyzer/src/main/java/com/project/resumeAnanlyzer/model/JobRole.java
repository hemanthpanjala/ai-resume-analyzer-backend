package com.project.resumeAnanlyzer.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "job_roles")
public class JobRole {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(length = 8000)
    private String description;

    @ElementCollection
    @CollectionTable(name = "job_role_skills", joinColumns = @JoinColumn(name = "job_role_id"))
    @Column(name = "skill")
    private List<String> requiredSkills = new ArrayList<>();

    public JobRole() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getRequiredSkills() { return requiredSkills; }
    public void setRequiredSkills(List<String> requiredSkills) {
        this.requiredSkills = (requiredSkills == null) ? new ArrayList<>() : requiredSkills;
    }
}
