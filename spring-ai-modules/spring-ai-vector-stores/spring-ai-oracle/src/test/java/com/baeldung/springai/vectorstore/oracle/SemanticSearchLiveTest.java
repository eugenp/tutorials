package com.baeldung.springai.vectorstore.oracle;

import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.oracle.OracleContainer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".*")
class SemanticSearchLiveTest {

    @Container
    private static OracleContainer oracleContainer = new OracleContainer("gvenzl/oracle-free:23-slim");

    @DynamicPropertySource
    private static void oracleDBProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", oracleContainer::getJdbcUrl);
        registry.add("spring.datasource.username", oracleContainer::getUsername);
        registry.add("spring.datasource.password", oracleContainer::getPassword);
    }

    private static final int MAX_RESULTS = 5;

    @Autowired
    private VectorStore vectorStore;

    @ParameterizedTest
    @ValueSource(strings = { "Sarcasm", "Regret", "Violence and Threats", "Greed, Power, and Money" })
    void whenSearchingBreakingBadTheme_thenRelevantQuotesReturned(String theme) {
        SearchRequest searchRequest = SearchRequest
            .builder()
            .query(theme)
            .topK(MAX_RESULTS)
            .build();

        List<Document> documents = vectorStore.similaritySearch(searchRequest);

        assertThat(documents)
            .hasSizeGreaterThan(0)
            .hasSizeLessThanOrEqualTo(MAX_RESULTS)
            .allSatisfy(document -> {
                assertThat(document.getText())
                    .isNotBlank();
                assertThat(String.valueOf(document.getMetadata().get("author")))
                    .isNotBlank();
            });
    }

    @ParameterizedTest
    @CsvSource({
        "Walter White, Pride",
        "Walter White, Control",
        "Jesse Pinkman, Abuse and foul language",
        "Mike Ehrmantraut, Wisdom",
        "Saul Goodman, Law"
    })
    void whenSearchingCharacterTheme_thenRelevantQuotesReturned(String author, String theme) {
        SearchRequest searchRequest = SearchRequest
            .builder()
            .query(theme)
            .topK(MAX_RESULTS)
            .filterExpression(String.format("author == '%s'", author))
            .build();

        List<Document> documents = vectorStore.similaritySearch(searchRequest);

        assertThat(documents)
            .hasSizeGreaterThan(0)
            .hasSizeLessThanOrEqualTo(MAX_RESULTS)
            .allSatisfy(document -> {
                assertThat(document.getText())
                    .isNotBlank();
                assertThat(String.valueOf(document.getMetadata().get("author")))
                    .contains(author);
            });
    }

}