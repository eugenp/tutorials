package com.baeldung.wildcardsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.DeleteIndexRequest;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class ElasticsearchWildcardServiceIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchWildcardServiceIntegrationTest.class);

    @Container
    static ElasticsearchContainer elasticsearchContainer = new ElasticsearchContainer(
            "docker.elastic.co/elasticsearch/elasticsearch:8.11.1")
            .withExposedPorts(9200)
            .withEnv("discovery.type", "single-node")
            .withEnv("xpack.security.enabled", "false")
            .withEnv("xpack.security.http.ssl.enabled", "false");

    @Autowired
    private ElasticsearchWildcardService wildcardService;

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    private static final String TEST_INDEX = "test_users";

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
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @AfterEach
    void cleanup() throws IOException {
        // Clean up test index
        DeleteIndexRequest deleteRequest = DeleteIndexRequest.of(d -> d
                .index(TEST_INDEX)
        );
        elasticsearchClient.indices().delete(deleteRequest);
    }

    @Test
    void testWildcardSearch_IntegrationTest() throws IOException {
        // When
        List<Map<String, Object>> results = wildcardService.wildcardSearchOnKeyword(TEST_INDEX, "name", "john*");

        // Then
        assertNotNull(results);
        assertFalse(results.isEmpty(), "Expected at least one result for 'john*'");

        logger.info("Found {} results for 'john*'", results.size());
        results.forEach(result -> logger.info("Result: {}", result));

        // Verify that all results contain names starting with "john" (case-insensitive)
        results.forEach(result -> {
            Object nameObj = result.get("name");
            assertNotNull(nameObj, "Name field should not be null");

            String name = nameObj.toString();
            logger.info("Checking name: '{}'", name);

            assertTrue(name.toLowerCase().startsWith("john"),
                    "Expected name to start with 'john', but got: " + name);
        });

        // Should find exactly 2 documents: "John Doe" and "Johnny Smith"
        assertEquals(2, results.size(), "Expected exactly 2 results for 'john*'");
    }

    @Test
    void testPrefixSearch_IntegrationTest() throws IOException {
        // When
        List<Map<String, Object>> results = wildcardService.prefixSearch(TEST_INDEX, "email", "john");

        // Then
        assertNotNull(results);
        assertFalse(results.isEmpty());

        // Verify that all results contain emails starting with "john"
        results.forEach(result -> {
            Object emailObj = result.get("email");
            assertNotNull(emailObj, "Email field should not be null");

            String email = emailObj.toString();
            assertTrue(email.startsWith("john"),
                    "Expected email to start with 'john', but got: " + email);
        });
    }

    @Test
    void testFuzzySearch_IntegrationTest() throws IOException {
        // When - search with typo
        List<Map<String, Object>> results = wildcardService.fuzzySearch(TEST_INDEX, "name", "jhon");

        // Then
        assertNotNull(results);
        assertFalse(results.isEmpty());

        // At least one result should contain a name similar to "john"
        boolean foundSimilar = results.stream()
                .anyMatch(result -> {
                    Object nameObj = result.get("name");
                    if (nameObj == null) return false;
                    String name = nameObj.toString().toLowerCase();
                    return name.contains("john");
                });
        assertTrue(foundSimilar, "Expected to find names similar to 'john'");
    }

    @Test
    void testAdvancedWildcardSearch_IntegrationTest() throws IOException {
        // When
        List<Map<String, Object>> results = wildcardService.advancedWildcardSearch(
                TEST_INDEX, "name", "*john*", "status", "active", "name");

        // Then
        assertNotNull(results);

        // Verify filtering worked - all results should have status "active"
        results.forEach(result -> {
            Object statusObj = result.get("status");
            assertNotNull(statusObj, "Status field should not be null");
            String status = statusObj.toString();
            assertEquals("active", status,
                    "Expected status to be 'active', but got: " + status);

            Object nameObj = result.get("name");
            assertNotNull(nameObj, "Name field should not be null");
            String name = nameObj.toString();
            assertTrue(name.toLowerCase().contains("john"),
                    "Expected name to contain 'john', but got: " + name);
        });
    }

    @Test
    void testWildcardSearch_WithContainsPattern() throws IOException {
        // When - search for names containing "John" anywhere
        List<Map<String, Object>> results = wildcardService.wildcardSearch(TEST_INDEX, "name", "*john*");

        // Then
        assertNotNull(results);
        assertTrue(results.size() >= 2, "Expected at least 2 results");

        // Verify results contain "john" somewhere in the name
        results.forEach(result -> {
            Object nameObj = result.get("name");
            assertNotNull(nameObj, "Name field should not be null");
            String name = nameObj.toString();
            assertTrue(name.toLowerCase().contains("john"),
                    "Expected name to contain 'john', but got: " + name);
        });
    }

    @Test
    void testMultiFieldWildcardSearch_IntegrationTest() throws IOException {
        // When
        List<Map<String, Object>> results = wildcardService.multiFieldWildcardSearch(
                TEST_INDEX, "john", "name", "email");

        // Then
        assertNotNull(results);
        assertFalse(results.isEmpty());

        // Results should have "john" in either name or email
        results.forEach(result -> {
            Object nameObj = result.get("name");
            Object emailObj = result.get("email");

            String name = nameObj != null ? nameObj.toString().toLowerCase() : "";
            String email = emailObj != null ? emailObj.toString().toLowerCase() : "";

            assertTrue(name.contains("john") || email.contains("john"),
                    "Expected 'john' in name or email");
        });
    }

    private void createTestIndex() throws IOException {
        // Create index with proper mapping for wildcard searches
        CreateIndexRequest createRequest = CreateIndexRequest.of(c -> c
                .index(TEST_INDEX)
                .mappings(m -> m
                        .properties("name", p -> p
                                .text(t -> t
                                        .fields("keyword", kf -> kf
                                                .keyword(k -> k)
                                        )
                                )
                        )
                        .properties("email", p -> p
                                .keyword(k -> k)
                        )
                        .properties("status", p -> p
                                .keyword(k -> k)
                        )
                )
        );

        elasticsearchClient.indices().create(createRequest);
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
        IndexRequest<Map<String, Object>> indexRequest = IndexRequest.of(i -> i
                .index(TEST_INDEX)
                .id(id)
                .document(document)
        );
        elasticsearchClient.index(indexRequest);
    }
}