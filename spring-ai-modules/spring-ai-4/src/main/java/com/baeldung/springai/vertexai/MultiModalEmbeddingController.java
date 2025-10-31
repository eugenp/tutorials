package com.baeldung.springai.vertexai;

import javax.validation.constraints.NotNull;

import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MultiModalEmbeddingController {

    private final MultiModalEmbeddingService embeddingService;

    public MultiModalEmbeddingController(MultiModalEmbeddingService embeddingService) {
        this.embeddingService = embeddingService;
    }

    @PostMapping("/embedding/image")
    public ResponseEntity<EmbeddingResponse> getEmbedding(@RequestParam("image") @NotNull MultipartFile imageFile) {
        EmbeddingResponse response = embeddingService.getEmbedding(
            MimeType.valueOf(imageFile.getContentType()),
            imageFile.getResource());
        return ResponseEntity.ok(response);
    }

}