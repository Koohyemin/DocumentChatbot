package com.project.docchatbot.document.dto;

import com.project.docchatbot.document.domain.Document;
import com.project.docchatbot.document.domain.DocumentStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DocumentUploadResponse {

    private Long documentId;
    private String title;
    private String originalFileName;
    private DocumentStatus status;

    // [A001] 파일 저장
    /**
     * {
     *   "documentId": 1,
     *   "title": "응답 json",
     *   "originalFileName": "sample.txt",
     *   "status": "UPLOADED"
     * }
     */
    public static DocumentUploadResponse from(Document document) {
        return DocumentUploadResponse.builder()
                .documentId(document.getId())
                .title(document.getTitle())
                .originalFileName(document.getOriginalFileName())
                .status(document.getStatus())
                .build();
    }
}