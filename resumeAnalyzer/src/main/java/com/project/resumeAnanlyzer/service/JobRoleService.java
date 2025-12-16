package com.project.resumeAnanlyzer.service;

import com.project.resumeAnanlyzer.model.JobRole;
import com.project.resumeAnanlyzer.repository.JobRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class JobRoleService {

    private final JobRoleRepository repo;

    public JobRoleService(JobRoleRepository repo) {
        this.repo = repo;
    }

    public JobRole create(String title, String description, List<String> requiredSkills) {
        JobRole jr = new JobRole();
        jr.setTitle(title);
        jr.setDescription(description);
        jr.setRequiredSkills(requiredSkills);
        return repo.save(jr);
    }

    public List<JobRole> findAll() {
        return repo.findAll();
    }

    public Optional<JobRole> findById(UUID id) {
        return repo.findById(id);
    }

    public JobRole update(UUID id, String title, String description, List<String> requiredSkills) {
        JobRole jr = repo.findById(id).orElseThrow(() -> new NoSuchElementException("JobRole not found"));
        jr.setTitle(title);
        jr.setDescription(description);
        jr.setRequiredSkills(requiredSkills);
        return repo.save(jr);
    }

    public void delete(UUID id) {
        repo.deleteById(id);
    }
}
