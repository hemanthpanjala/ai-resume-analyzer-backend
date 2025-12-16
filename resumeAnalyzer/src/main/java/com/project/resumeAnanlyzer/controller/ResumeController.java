package com.project.resumeAnanlyzer.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    @PostMapping(
            value = "/extract-text",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Map<String, String> extractText(@RequestPart("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        if (!file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
            throw new IllegalArgumentException("Only PDF files are supported");
        }

        String text;
        try (InputStream is = file.getInputStream();
             PDDocument document = PDDocument.load(is)) {

            PDFTextStripper stripper = new PDFTextStripper();
            text = stripper.getText(document);
        }

        // simple cleanup
        text = text.replaceAll("\\r", "").trim();

        return Map.of("text", text);
    }
}
