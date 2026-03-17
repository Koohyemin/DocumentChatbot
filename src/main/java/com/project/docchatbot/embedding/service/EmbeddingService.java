package com.project.docchatbot.embedding.service;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmbeddingService {

    private final EmbeddingModel embeddingModel;

    public EmbeddingService(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel; // 텍스트를 수치 벡터로 변경하는 공통 인터페이스
    }

    public List<Double> embed(String text) {
        float[] output = embeddingModel.embed(text);
        return convertToDoubleList(output);
    }

    // DB 벡터 값을 문자열 방식으로 넣음 ([0.12,0.34,0.56,...])
    public String toVectorLiteral(List<Double> embedding) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < embedding.size(); i++) {
            sb.append(embedding.get(i));
            if (i < embedding.size() - 1) {
                sb.append(",");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    private List<Double> convertToDoubleList(float[] values) {
        List<Double> result = new ArrayList<>(values.length);

        for (float v : values) {
            result.add((double) v);
        }

        return result;
    }
}