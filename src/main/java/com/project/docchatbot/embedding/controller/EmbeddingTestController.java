package com.project.docchatbot.embedding.controller;

import com.project.docchatbot.embedding.service.EmbeddingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmbeddingTestController {

    private final EmbeddingService embeddingService;

    public EmbeddingTestController(EmbeddingService embeddingService) {
        this.embeddingService = embeddingService;
    }

    @GetMapping("/api/test/embedding")
    public String test(@RequestParam(defaultValue = "연차는 다음 해 1분기까지 사용할 수 있다.") String text) {
        List<Double> embedding = embeddingService.embed(text);
        return "embedding size = " + embedding.size();
    }
}