package com.project.docchatbot.document.service;

import com.project.docchatbot.common.util.TextChunkUtil;
import com.project.docchatbot.document.domain.Document;
import com.project.docchatbot.document.domain.DocumentChunk;
import com.project.docchatbot.document.domain.DocumentStatus;
import com.project.docchatbot.document.dto.DocumentUploadResponse;
import com.project.docchatbot.document.repository.DocumentChunkRepository;
import com.project.docchatbot.document.repository.DocumentRepository;
import com.project.docchatbot.embedding.service.EmbeddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentChunkRepository documentChunkRepository;
    private final EmbeddingService embeddingService;

    private final String uploadDir = "uploads";

    // [A001] 파일 저장
    public DocumentUploadResponse uploadDocument(String title, MultipartFile file) throws IOException {

        // 1. 업로드 폴더 생성
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 2. 파일 저장 경로 생성
        String originalFileName = file.getOriginalFilename();
        String storedFilePath = uploadDir + "/" + originalFileName;

        // 3. 파일 저장
        Path filePath = Paths.get(storedFilePath);
        file.transferTo(filePath);

        // 4.Document 엔티티 생성
        Document document = new Document(
                title,
                originalFileName,
                storedFilePath,
                file.getContentType(),
                file.getSize(),
                DocumentStatus.UPLOADED
                // 생성 시간, 업데이트 시간은 default 현재시간
        );

        // 5. DB 저장
        Document savedDocument = documentRepository.save(document);
        // txt / md 파일만 우선 chunk 저장
        try {
            if (isTextFile(originalFileName)) {
                saveChunks(savedDocument, filePath);
            }
            // 문서 상태 값 관리
            savedDocument.updateStatus(DocumentStatus.EMBEDDED);

        } catch (Exception e) {
            savedDocument.markFailed(e.getMessage()); // 임베딩 실패시 예외 처리
            throw e;
        }

        // 6. 응답 반환
        return DocumentUploadResponse.from(savedDocument);
    }

    // [임시] txt, md 파일만 분리하기 → 추후 pdf 추가 예정
    private boolean isTextFile(String fileName) {
        if (fileName == null) {
            return false;
        }

        String lower = fileName.toLowerCase();
        return lower.endsWith(".txt") || lower.endsWith(".md");
    }

    private void saveChunks(Document document, Path filePath) throws IOException {
        String content = Files.readString(filePath, StandardCharsets.UTF_8);

        List<String> chunks = TextChunkUtil.split(content, 500, 100);
        List<DocumentChunk> documentChunks = new ArrayList<>();

        for (int i = 0; i < chunks.size(); i++) {
            documentChunks.add(new DocumentChunk(document, i, chunks.get(i)));
        }

        documentChunkRepository.saveAll(documentChunks);

        for (DocumentChunk chunk : documentChunks) {
            List<Double> embedding = embeddingService.embed(chunk.getContent());
            String vectorLiteral = embeddingService.toVectorLiteral(embedding);

            documentChunkRepository.updateEmbedding(chunk.getId(), vectorLiteral);
        }
    }
/*    private void saveChunks(Document document, Path filePath) throws IOException {
        String content = Files.readString(filePath, StandardCharsets.UTF_8);

        // 500자씩 자르고, 100자는 겹치게 설정
        List<String> chunks = TextChunkUtil.split(content, 500, 100);
        List<DocumentChunk> documentChunks = new ArrayList<>();

        // 문서 내 순서 보장
        for (int i = 0; i < chunks.size(); i++) {
            String chunkText = chunks.get(i);

            DocumentChunk documentChunk = new DocumentChunk(document, i, chunkText);

            List<Double> embedding = embeddingService.embed(chunkText);
            String vectorLiteral = embeddingService.toVectorLiteral(embedding);

            documentChunk.updateEmbedding(vectorLiteral);
            documentChunks.add(documentChunk);
        }

        documentChunkRepository.saveAll(documentChunks);
    }*/
}