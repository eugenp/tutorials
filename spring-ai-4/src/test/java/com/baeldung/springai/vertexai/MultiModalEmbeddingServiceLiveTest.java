package com.baeldung.springai.vertexai;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.MimeTypeUtils;

@SpringBootTest
@ActiveProfiles("vertexai")
class MultiModalEmbeddingServiceLiveTest {

    private static final String IMAGE_PATH = "image/chiikawa.png";

    @Autowired
    private MultiModalEmbeddingService embeddingService;

    @Test
    void whenGetEmbeddings_thenReturnEmbeddingResponse() {
        Resource imageResource = new ClassPathResource(IMAGE_PATH);
        EmbeddingResponse response = embeddingService.getEmbedding(MimeTypeUtils.IMAGE_PNG, imageResource);
        assertThat(response).isNotNull();
        assertThat(response.getResults()).isNotNull();
        assertThat(response.getResults().isEmpty()).isFalse();
    }

}