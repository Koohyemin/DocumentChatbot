package com.project.docchatbot.document.controller;

import com.project.docchatbot.document.dto.DocumentUploadResponse;
import com.project.docchatbot.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/documents")
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping
    public DocumentUploadResponse uploadDocument(
            @RequestParam("title") String title,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        return documentService.uploadDocument(title, file);
    }
}
