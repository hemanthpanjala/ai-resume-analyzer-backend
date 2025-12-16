package com.project.resumeAnanlyzer.controller;

import com.project.resumeAnanlyzer.dto.CreateJobRoleRequest;
import com.project.resumeAnanlyzer.dto.JobRoleResponse;
import com.project.resumeAnanlyzer.dto.UpdateJobRoleRequest;
import com.project.resumeAnanlyzer.model.JobRole;
import com.project.resumeAnanlyzer.service.JobRoleService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/job-roles")
public class JobRoleController {

    private final JobRoleService jobRoleService;

    public JobRoleController(JobRoleService jobRoleService) {
        this.jobRoleService = jobRoleService;
    }

    @PostMapping
    public JobRoleResponse create(@Valid @RequestBody CreateJobRoleRequest req) {
        JobRole jobRole = jobRoleService.create(
                req.getTitle(),
                req.getDescription(),
                req.getRequiredSkills()
        );
        return toResponse(jobRole);
    }

    @GetMapping
    public List<JobRoleResponse> list() {
        return jobRoleService.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public JobRoleResponse get(@PathVariable UUID id) {
        JobRole jobRole = jobRoleService.findById(id)
                .orElseThrow(() -> new NoSuchElementException("JobRole not found"));
        return toResponse(jobRole);
    }

    @PutMapping("/{id}")
    public JobRoleResponse update(@PathVariable UUID id,
                                  @Valid @RequestBody UpdateJobRoleRequest req) {
        JobRole jobRole = jobRoleService.update(
                id,
                req.getTitle(),
                req.getDescription(),
                req.getRequiredSkills()
        );
        return toResponse(jobRole);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        jobRoleService.delete(id);
    }

    private JobRoleResponse toResponse(JobRole jobRole) {
        return new JobRoleResponse(
                jobRole.getId(),
                jobRole.getTitle(),
                jobRole.getDescription(),
                jobRole.getRequiredSkills()
        );
    }
}

