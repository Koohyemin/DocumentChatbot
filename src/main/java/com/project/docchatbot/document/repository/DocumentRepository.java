package com.project.docchatbot.document.repository;

import com.project.docchatbot.document.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}