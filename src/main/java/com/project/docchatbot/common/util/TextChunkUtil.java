package com.project.docchatbot.common.util;

import java.util.ArrayList;
import java.util.List;

public class TextChunkUtil {

    private TextChunkUtil() {
    }

    // [A002] Chunk Util
    // overlapSize : 문맥이 깨지지 않도록 오버랩 유지
    public static List<String> split(String text, int chunkSize, int overlapSize) {
        validate(text, chunkSize, overlapSize);

        List<String> chunks = new ArrayList<>();
        int start = 0;
        int textLength = text.length();

        while (start < textLength) {
            int end = Math.min(start + chunkSize, textLength);
            String chunk = text.substring(start, end).trim();

            if (!chunk.isEmpty()) {
                chunks.add(chunk);
            }

            if (end == textLength) {
                break;
            }

            start = end - overlapSize;
        }

        return chunks;
    }

    private static void validate(String text, int chunkSize, int overlapSize) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("텍스트가 비어 있습니다.");
        }

        if (chunkSize <= 0) {
            throw new IllegalArgumentException("chunkSize는 0보다 커야 합니다.");
        }

        if (overlapSize < 0) {
            throw new IllegalArgumentException("overlapSize는 0 이상이어야 합니다.");
        }

        if (overlapSize >= chunkSize) {
            throw new IllegalArgumentException("overlapSize는 chunkSize보다 작아야 합니다.");
        }
    }
}