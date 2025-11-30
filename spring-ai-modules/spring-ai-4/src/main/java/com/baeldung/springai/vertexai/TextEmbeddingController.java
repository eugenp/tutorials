package com.baeldung.springai.vertexai;

import javax.validation.constraints.NotNull;

import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TextEmbeddingController {

    private final TextEmbeddingService textEmbeddingService;

    public TextEmbeddingController(TextEmbeddingService textEmbeddingService) {
        this.textEmbeddingService = textEmbeddingService;
    }

    @PostMapping("/embedding/text")
    public ResponseEntity<EmbeddingResponse> getEmbedding(@RequestBody @NotNull String text) {
        EmbeddingResponse response = textEmbeddingService.getEmbedding(text);
        return ResponseEntity.ok(response);
    }

}