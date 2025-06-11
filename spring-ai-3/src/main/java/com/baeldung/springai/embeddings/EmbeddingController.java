package com.baeldung.springai.embeddings;

import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmbeddingController {

    private final EmbeddingService embeddingService;
    private final ManualEmbeddingService manualEmbeddingService;

    public EmbeddingController(EmbeddingService embeddingService, ManualEmbeddingService manualEmbeddingService) {
        this.embeddingService = embeddingService;
        this.manualEmbeddingService = manualEmbeddingService;
    }

    @PostMapping("/embeddings")
    public ResponseEntity<EmbeddingResponse> getEmbeddings(@RequestBody String text) {
        EmbeddingResponse response = embeddingService.getEmbeddings(text);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/manual-embeddings")
    public ResponseEntity<EmbeddingResponse> getManualEmbeddings(@RequestBody String text) {
        EmbeddingResponse response = manualEmbeddingService.getEmbeddings(text);
        return ResponseEntity.ok(response);
    }

}
