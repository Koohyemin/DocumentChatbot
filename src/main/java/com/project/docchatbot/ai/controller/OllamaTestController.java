package com.project.docchatbot.ai.controller;

import com.project.docchatbot.ai.service.AiAnswerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OllamaTestController {

    private final AiAnswerService aiAnswerService;

    public OllamaTestController(AiAnswerService aiAnswerService) {
        this.aiAnswerService = aiAnswerService;
    }

    @GetMapping("/api/test/ollama")
    public String test(@RequestParam(defaultValue = "안녕하세요 자기소개 해주세요") String message) {
        return aiAnswerService.ask(message);
    }
}