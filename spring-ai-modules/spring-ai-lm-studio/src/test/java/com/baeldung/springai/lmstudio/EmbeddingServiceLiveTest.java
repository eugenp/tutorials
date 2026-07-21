package com.baeldung.springai.lmstudio;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("lm-studio")
class EmbeddingServiceLiveTest {

    private static final String TEXT = "Spring AI with LM Studio";

    @Autowired
    private EmbeddingService embeddingService;

    @Test
    void whenEmbeddingServiceIsCalled_thenResponseContainsEmbeddings() {
        EmbeddingResponse response = embeddingService.getEmbeddings(TEXT);
        assertThat(response).isNotNull();
        assertThat(response.getResults()).isNotEmpty();
    }
}
