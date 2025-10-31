package com.baeldung.springai.chromadb;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class SemanticSearchLiveTest {

    private static final int MAX_RESULTS = 3;

    @Autowired
    private VectorStore vectorStore;

    @ParameterizedTest
    @ValueSource(strings = {"Love and Romance", "Time and Mortality", "Jealousy and Betrayal"})
    void whenSearchingShakespeareTheme_thenRelevantPoemsReturned(String theme) {
        SearchRequest searchRequest = SearchRequest
            .builder()
            .query(theme)
            .topK(MAX_RESULTS)
            .build();
        List<Document> documents = vectorStore.similaritySearch(searchRequest);

        assertThat(documents)
            .hasSizeLessThanOrEqualTo(MAX_RESULTS)
            .allSatisfy(document -> {
                String title = String.valueOf(document.getMetadata().get("title"));
                assertThat(title)
                    .isNotBlank();
            });
    }

}