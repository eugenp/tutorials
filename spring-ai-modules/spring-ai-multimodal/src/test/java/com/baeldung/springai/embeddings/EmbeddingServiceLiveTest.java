package com.baeldung.springai.embeddings;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("embeddings")
class EmbeddingServiceLiveTest {

    @Autowired
    private EmbeddingService embeddingService;

    @Test
    void whenGetEmbeddings_thenReturnEmbeddingResponse() {
        String text = "This is a test string for embedding.";
        EmbeddingResponse response = embeddingService.getEmbeddings(text);
        assertThat(response).isNotNull();
        assertThat(response.getResults()).isNotNull();
        assertThat(response.getResults().isEmpty()).isFalse();
    }

}