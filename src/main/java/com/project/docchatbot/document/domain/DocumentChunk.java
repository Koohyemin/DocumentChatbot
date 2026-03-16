package com.project.docchatbot.document.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "document_chunks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DocumentChunk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // chunk는 여러개가 하나의 document에 속함 (1:N)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Column(name = "chunk_index", nullable = false)
    private Integer chunkIndex;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

/*    @Column(name = "embedding", columnDefinition = "vector(768)")
    private String embedding;*/
    @Column(name = "embedding", columnDefinition = "vector(768)", insertable = false, updatable = false)
    private String embedding;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public DocumentChunk(Document document, Integer chunkIndex, String content) {
        this.document = document;
        this.chunkIndex = chunkIndex;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    public void updateEmbedding(String embedding) {
        this.embedding = embedding;
    }
}