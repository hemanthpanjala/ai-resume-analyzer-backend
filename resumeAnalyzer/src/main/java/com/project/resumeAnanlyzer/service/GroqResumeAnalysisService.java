package com.project.resumeAnanlyzer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.resumeAnanlyzer.dto.AiAnalysisResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Map;

@Service
public class GroqResumeAnalysisService {

    private final WebClient webClient;
    private final String model;
    private final ObjectMapper mapper = new ObjectMapper();

    public GroqResumeAnalysisService(
            @Value("${groq.api.url}") String apiUrl,
            @Value("${groq.api.key}") String apiKey,
            @Value("${groq.model}") String model
    ) {
        this.model = model;

        System.out.println("✅ Groq model in use: " + model);

        this.webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public AiAnalysisResult analyze(
            String resumeText,
            String jobRoleTitle,
            String jobRoleDescription,
            List<String> requiredSkills
    ) {

        // ------------------ SAFETY GUARDS ------------------

        if (resumeText == null || resumeText.isBlank()) {
            throw new IllegalArgumentException("Resume text cannot be empty");
        }

        String trimmedResume =
                resumeText.length() > 12000
                        ? resumeText.substring(0, 12000)
                        : resumeText;

        String skillsJoined =
                (requiredSkills == null || requiredSkills.isEmpty())
                        ? ""
                        : String.join(", ", requiredSkills);

        // ------------------ PROMPTS ------------------

        String systemPrompt = """
                You are an expert technical recruiter.
                You analyze resumes strictly and return JSON only.
                Do not include markdown or explanations.
                """;

        String userPrompt = """
                Evaluate the resume against the job role below.

                Job Role Title:
                %s

                Job Role Description:
                %s

                Required Skills:
                %s

                Resume Text:
                %s

                Return a JSON object with EXACTLY these fields:
                {
                  "matchScore": number (0-100),
                  "strengths": array of strings,
                  "weaknesses": array of strings,
                  "missingSkills": array of strings,
                  "improvementSuggestions": array of strings
                }

                Return ONLY valid JSON. No extra text.
                """
                .formatted(
                        jobRoleTitle,
                        jobRoleDescription,
                        skillsJoined,
                        trimmedResume
                );

        Map<String, Object> requestBody = Map.of(
                "model", model,
                "temperature", 0.2,
                "max_tokens", 800,
                "messages", List.of(
                        Map.of("role", "system", "content", systemPrompt),
                        Map.of("role", "user", "content", userPrompt)
                )
        );

        // ------------------ GROQ API CALL ------------------

        Map<String, Object> response;

        try {
            response = webClient.post()
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        } catch (WebClientResponseException e) {
            String errorBody = e.getResponseBodyAsString();
            throw new RuntimeException(
                    "❌ Groq API ERROR (" + e.getStatusCode().value() + "): " + errorBody,
                    e
            );
        }

        if (response == null || !response.containsKey("choices")) {
            throw new RuntimeException("❌ Empty or invalid response from Groq API");
        }

        // ------------------ PARSE RESPONSE ------------------

        try {
            List<Map<String, Object>> choices =
                    (List<Map<String, Object>>) response.get("choices");

            Map<String, Object> message =
                    (Map<String, Object>) choices.get(0).get("message");

            String content = (String) message.get("content");

            // Remove accidental markdown fences if Groq adds them
            content = content
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();

            return mapper.readValue(content, AiAnalysisResult.class);

        } catch (Exception e) {
            throw new RuntimeException(
                    "❌ Failed to parse Groq response into AiAnalysisResult",
                    e
            );
        }
    }
}
