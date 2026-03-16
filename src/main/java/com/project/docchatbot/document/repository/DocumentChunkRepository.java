package com.project.docchatbot.document.repository;

import com.project.docchatbot.document.domain.DocumentChunk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DocumentChunkRepository extends JpaRepository<DocumentChunk, Long> {

    @Modifying
    @Query(value = """
    UPDATE document_chunks
    SET embedding = CAST(:embedding AS vector)
    WHERE id = :chunkId
    """, nativeQuery = true)
    void updateEmbedding(@Param("chunkId") Long chunkId,
                         @Param("embedding") String embedding);
}