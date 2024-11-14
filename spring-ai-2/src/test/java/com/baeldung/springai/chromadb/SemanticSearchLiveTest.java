package com.baeldung.springai.chromadb;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;
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
    private static final List<String> THEMES = List.of("Love and Romance", "Time and Mortality", "Jealousy and Betrayal");

    @Autowired
    private VectorStore vectorStore;

    @ParameterizedTest
    @FieldSource("THEMES")
    void whenSearchingShakespeareTheme_thenRelevantPoemsReturned(String theme) {
        SearchRequest searchRequest = SearchRequest
            .query(theme)
            .withTopK(MAX_RESULTS);
        List<Document> response = vectorStore.similaritySearch(searchRequest);

        assertThat(response)
            .hasSizeLessThanOrEqualTo(MAX_RESULTS);
    }

}