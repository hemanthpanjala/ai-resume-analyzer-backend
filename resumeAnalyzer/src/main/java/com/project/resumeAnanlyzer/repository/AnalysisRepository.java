package com.project.resumeAnanlyzer.repository;

import com.project.resumeAnanlyzer.model.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnalysisRepository extends JpaRepository<Analysis, UUID> {

    List<Analysis> findByUserIdOrderByCreatedAtDesc(UUID userId);

    List<Analysis> findTop3ByUserIdOrderByCreatedAtDesc(UUID userId);

    Optional<Analysis> findByIdAndUserId(UUID id, UUID userId);

    long countByUserId(UUID userId);
}
