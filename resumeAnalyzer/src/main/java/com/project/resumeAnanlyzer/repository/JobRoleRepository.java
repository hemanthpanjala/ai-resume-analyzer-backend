package com.project.resumeAnanlyzer.repository;

import com.project.resumeAnanlyzer.model.JobRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobRoleRepository extends JpaRepository<JobRole, UUID> {}
