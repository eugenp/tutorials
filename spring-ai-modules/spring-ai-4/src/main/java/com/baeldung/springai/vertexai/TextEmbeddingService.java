package com.baeldung.springai.vertexai;

import java.util.Arrays;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Service;

@Service
public class TextEmbeddingService {

    private final EmbeddingModel embeddingModel;

    public TextEmbeddingService(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    public EmbeddingResponse getEmbedding(String... texts) {
        EmbeddingRequest request = new EmbeddingRequest(Arrays.asList(texts), null);
        return embeddingModel.call(request);
    }

}
