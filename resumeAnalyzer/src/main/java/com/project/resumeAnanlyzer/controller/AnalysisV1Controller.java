package com.project.resumeAnanlyzer.controller;

import com.project.resumeAnanlyzer.dto.AiAnalysisResult;
import com.project.resumeAnanlyzer.dto.AnalysisDetailResponse;
import com.project.resumeAnanlyzer.dto.AnalysisSummaryResponse;
import com.project.resumeAnanlyzer.dto.RunAnalysisRequest;
import com.project.resumeAnanlyzer.model.Analysis;
import com.project.resumeAnanlyzer.repository.AnalysisRepository;
import com.project.resumeAnanlyzer.service.GroqResumeAnalysisService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/analyses")
@CrossOrigin(origins = "http://localhost:4200")
public class AnalysisV1Controller {

    private final GroqResumeAnalysisService groqService;
    private final AnalysisRepository analysisRepository;

    public AnalysisV1Controller(GroqResumeAnalysisService groqService,
                                AnalysisRepository analysisRepository) {
        this.groqService = groqService;
        this.analysisRepository = analysisRepository;
    }

    // POST /api/v1/analyses/run  (JWT required)
    @PostMapping("/run")
    public AnalysisDetailResponse run(@RequestBody RunAnalysisRequest request) {

        if (request.getJobRoleId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "jobRoleId is required");
        }
        if (request.getResumeText() == null || request.getResumeText().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "resumeText is required");
        }

        // Your Groq service needs these. Your Angular *can* send them.
        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "title is required");
        }
        if (request.getRequiredSkills() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "requiredSkills is required");
        }

        UUID userId = currentUserId();

        AiAnalysisResult ai = groqService.analyze(
                request.getResumeText(),
                request.getTitle(),
                request.getDescription(),
                request.getRequiredSkills()
        );

        Analysis a = new Analysis();
        a.setUserId(userId);

        a.setJobRoleId(request.getJobRoleId());
        a.setJobRoleTitle(request.getTitle());
        a.setMatchScore(ai.getMatchScore());

        // Your entity stores Strings -> join lists into readable blocks
        a.setStrengths(joinLines(ai.getStrengths()));
        a.setWeaknesses(joinLines(ai.getWeaknesses()));
        a.setMissingSkills(joinLines(ai.getMissingSkills()));
        a.setImprovementSuggestions(joinLines(ai.getImprovementSuggestions()));

        Analysis saved = analysisRepository.save(a);
        return AnalysisDetailResponse.from(saved);
    }

    // GET /api/v1/analyses  (JWT required)
    @GetMapping
    public List<AnalysisSummaryResponse> list() {
        UUID userId = currentUserId();
        return analysisRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(AnalysisSummaryResponse::from)
                .toList();
    }

    // GET /api/v1/analyses/{id}  (JWT required)
    @GetMapping("/{id}")
    public AnalysisDetailResponse get(@PathVariable UUID id) {
        UUID userId = currentUserId();

        Analysis a = analysisRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Analysis not found"));

        return AnalysisDetailResponse.from(a);
    }

    private UUID currentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getPrincipal() == null) {
            throw new IllegalStateException("No authenticated user found");
        }

        String principal = auth.getPrincipal().toString();

        if ("anonymousUser".equals(principal)) {
            throw new IllegalStateException("No authenticated user found");
        }

        return UUID.fromString(principal);
    }

    private String joinLines(List<String> items) {
        if (items == null || items.isEmpty()) return "";
        return String.join("\n", items);
    }
}
