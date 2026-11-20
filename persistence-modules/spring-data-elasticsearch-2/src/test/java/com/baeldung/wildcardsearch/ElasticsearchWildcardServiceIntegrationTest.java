package com.baeldung.wildcardsearch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.DeleteIndexRequest;

@SpringBootTest
@Testcontainers
class ElasticsearchWildcardServiceIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchWildcardServiceIntegrationTest.class);
    private static final String TEST_INDEX = "test_users";

    @Autowired
    private ElasticsearchWildcardService wildcardService;

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Container
    static ElasticsearchContainer elasticsearchContainer = new ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch:8.11.1").withExposedPorts(
            9200)
        .withEnv("discovery.type", "single-node")
        .withEnv("xpack.security.enabled", "false")
        .withEnv("xpack.security.http.ssl.enabled", "false");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("elasticsearch.host", elasticsearchContainer::getHost);
        registry.add("elasticsearch.port", () -> elasticsearchContainer.getMappedPort(9200));
    }

    @BeforeEach
    void setUp() throws IOException {
        // Create test index
        createTestIndex();

        // Index sample documents
        indexSampleDocuments();

        // Wait for documents to be indexed
        // Wait until documents are actually indexed
        waitUntilDocumentsIndexed(); // Adjust expected count based on your test data
    }

    private void waitUntilDocumentsIndexed() {
        await().atMost(Duration.ofSeconds(10))
            .pollInterval(Duration.ofMillis(100))
            .pollDelay(Duration.ofMillis(50))
            .untilAsserted(() -> {

                List<Map<String, Object>> results = wildcardService.wildcardSearch(TEST_INDEX, "name", "j*");
                assertThat(results).as("Expected at least %d documents to be indexed", 3)
                    .hasSizeGreaterThanOrEqualTo(3);
            });
    }

    @AfterEach
    void cleanup() throws IOException {
        // Clean up test index
        DeleteIndexRequest deleteRequest = DeleteIndexRequest.of(d -> d.index(TEST_INDEX));
        elasticsearchClient.indices()
            .delete(deleteRequest);
    }

    @Test
    void whenWildcardSearchOnKeyword_thenReturnMatchingDocuments() throws IOException {
        // When
        List<Map<String, Object>> results = wildcardService.wildcardSearch(TEST_INDEX, "name", "john*");

        // Then
        assertThat(results).isNotEmpty()
            .hasSize(2)
            .extracting(result -> result.get("name"))
            .doesNotContainNull()
            .extracting(Object::toString)
            .allSatisfy(name -> assertThat(name.toLowerCase()).startsWith("john"));

        logger.info("Found {} results for 'john*'", results.size());
    }

    @Test
    void whenPrefixSearch_thenReturnDocumentsWithMatchingPrefix() throws IOException {
        // When
        List<Map<String, Object>> results = wildcardService.prefixSearch(TEST_INDEX, "email", "john");

        // Then
        assertThat(results).isNotEmpty()
            .extracting(result -> result.get("email"))
            .doesNotContainNull()
            .extracting(Object::toString)
            .allSatisfy(email -> assertThat(email).startsWith("john"));
    }

    @Test
    void givenSearchTermWithTypo_whenFuzzySearch_thenReturnSimilarMatches() throws IOException {
        // When - search with typo
        List<Map<String, Object>> results = wildcardService.fuzzySearch(TEST_INDEX, "name", "jhon");

        // Then
        assertThat(results).isNotEmpty()
            .extracting(result -> result.get("name"))
            .doesNotContainNull()
            .extracting(Object::toString)
            .extracting(name -> name.toLowerCase())
            .anySatisfy(name -> assertThat(name).contains("john"));
    }

    @Test
    void whenAdvancedWildcardSearch_thenReturnFilteredAndMatchingResults() throws IOException {
        // When - search for names containing "john" with status "active"
        // Should return doc1 (John Doe) and doc3 (Johnny Smith)
        List<Map<String, Object>> results = wildcardService.advancedWildcardSearch(TEST_INDEX, "name", "*john*", "status", "active", "name");

        // Then
        assertThat(results).isNotEmpty()
            .hasSize(2)  // Exactly 2 documents match
            .allSatisfy(result -> {
                assertThat(result.get("status")).isNotNull()
                    .extracting(Object::toString)
                    .isEqualTo("active");

                assertThat(result.get("name")).isNotNull()
                    .extracting(Object::toString)
                    .asString()
                    .containsIgnoringCase("john");
            });
    }

    @Test
    void whenWildcardSearchWithContainsPattern_thenReturnDocumentsContainingPattern() throws IOException {
        // When - search for names containing "John" anywhere
        List<Map<String, Object>> results = wildcardService.wildcardSearch(TEST_INDEX, "name", "*john*");

        // Then
        assertThat(results).hasSizeGreaterThanOrEqualTo(2)
            .extracting(result -> result.get("name"))
            .doesNotContainNull()
            .extracting(Object::toString)
            .allSatisfy(name -> assertThat(name.toLowerCase()).contains("john"));
    }

    @Test
    void whenMultiFieldWildcardSearch_thenReturnDocumentsMatchingAnyField() throws IOException {
        // When
        List<Map<String, Object>> results = wildcardService.multiFieldWildcardSearch(TEST_INDEX, "john", "name", "email");

        // Then
        assertThat(results).isNotEmpty()
            .allSatisfy(result -> {
                String name = result.get("name") != null ? result.get("name")
                    .toString()
                    .toLowerCase() : "";
                String email = result.get("email") != null ? result.get("email")
                    .toString()
                    .toLowerCase() : "";
                assertThat(name.contains("john") || email.contains("john")).as("Expected 'john' in name or email")
                    .isTrue();
            });
    }

    private void createTestIndex() throws IOException {
        // Create index with proper mapping for wildcard searches
        CreateIndexRequest createRequest = CreateIndexRequest.of(c -> c.index(TEST_INDEX)
            .mappings(m -> m.properties("name", p -> p.text(t -> t.fields("keyword", kf -> kf.keyword(k -> k))))
                .properties("email", p -> p.keyword(k -> k))
                .properties("status", p -> p.keyword(k -> k))));

        elasticsearchClient.indices()
            .create(createRequest);
        logger.debug("Created test index {} with proper mapping", TEST_INDEX);
    }

    private void indexSampleDocuments() throws IOException {
        // Create sample documents
        Map<String, Object> doc1 = new HashMap<>();
        doc1.put("name", "John Doe");
        doc1.put("email", "john.doe@example.com");
        doc1.put("status", "active");

        Map<String, Object> doc2 = new HashMap<>();
        doc2.put("name", "Jane Johnson");
        doc2.put("email", "jane.johnson@example.com");
        doc2.put("status", "inactive");

        Map<String, Object> doc3 = new HashMap<>();
        doc3.put("name", "Johnny Smith");
        doc3.put("email", "johnny.smith@example.com");
        doc3.put("status", "active");

        // Index documents
        indexDocument("1", doc1);
        indexDocument("2", doc2);
        indexDocument("3", doc3);
    }

    private void indexDocument(String id, Map<String, Object> document) throws IOException {
        IndexRequest<Map<String, Object>> indexRequest = IndexRequest.of(i -> i.index(TEST_INDEX)
            .id(id)
            .document(document));
        elasticsearchClient.index(indexRequest);
    }
}