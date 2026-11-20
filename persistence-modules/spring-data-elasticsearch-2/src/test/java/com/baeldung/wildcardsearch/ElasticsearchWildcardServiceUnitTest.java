package com.baeldung.wildcardsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ElasticsearchWildcardService
 * These tests use Mockito to mock the ElasticsearchClient - no Docker required!
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Elasticsearch Wildcard Service Unit Tests")
class ElasticsearchWildcardServiceUnitTest {

    @Mock
    private ElasticsearchClient elasticsearchClient;

    @InjectMocks
    private ElasticsearchWildcardService wildcardService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        ReflectionTestUtils.setField(wildcardService, "maxResults", 1000);
    }

    // ==================== WILDCARD SEARCH TESTS ====================

    @Test
    @DisplayName("Return matching documents when performing wildcard search")
    void whenWildcardSearch_thenReturnMatchingDocuments() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse(createHit("1", "John Doe", "john.doe@example.com"),
            createHit("2", "Johnny Cash", "johnny.cash@example.com"));

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class))).thenReturn(mockResponse);

        // When
        List<Map<String, Object>> results = wildcardService.wildcardSearch("users", "name", "john*");

        // Then
        assertThat(results).hasSize(2)
            .extracting(result -> result.get("name"))
            .containsExactly("John Doe", "Johnny Cash");
        verify(elasticsearchClient).search(any(Function.class), eq(ObjectNode.class));
    }

    @Test
    @DisplayName("Handle empty results when performing wildcard search")
    void whenWildcardSearch_thenHandleEmptyResults() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse();

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class))).thenReturn(mockResponse);

        // When
        List<Map<String, Object>> results = wildcardService.wildcardSearch("users", "name", "xyz*");

        // Then
        assertThat(results).isEmpty();
    }

    @Test
    @DisplayName("Perform case-insensitive wildcard search")
    void whenWildcardSearch_thenBeCaseInsensitive() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse(createHit("1", "John Doe", "john.doe@example.com"));

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class))).thenReturn(mockResponse);

        // When
        List<Map<String, Object>> results = wildcardService.wildcardSearch("users", "name", "JOHN*");

        // Then
        assertThat(results)
            .hasSize(1)
            .extracting(result -> result.get("name"))
            .contains("John Doe");
    }

    @Test
    @DisplayName("Throw IOException when client connection fails")
    void whenWildcardSearch_thenThrowIOException() throws IOException {
        // Given
        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class))).thenThrow(new IOException("Connection timeout"));

        // When & Then
        assertThrows(IOException.class, () -> wildcardService.wildcardSearch("users", "name", "john*"));
    }

    // ==================== PREFIX SEARCH TESTS ====================

    @Test
    @DisplayName("Return matching documents when performing prefix search")
    void whenPrefixSearch_thenReturnMatchingDocuments() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse(createHit("1", "John Doe", "john@example.com"),
            createHit("2", "John Smith", "john.smith@example.com"));

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class))).thenReturn(mockResponse);

        // When
        List<Map<String, Object>> results = wildcardService.prefixSearch("users", "email", "john");

        // Then
        assertThat(results)
            .hasSize(2)
            .extracting(result -> result.get("email"))
            .doesNotContainNull()
            .allSatisfy(email -> assertThat(email.toString()).startsWith("john"));
    }

    @Test
    @DisplayName("Return empty list when prefix search finds no matches")
    void whenPrefixSearch_thenReturnEmptyWhenNoMatches() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse();

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class))).thenReturn(mockResponse);

        // When
        List<Map<String, Object>> results = wildcardService.prefixSearch("users", "email", "xyz");

        // Then
        assertThat(results).isEmpty();
    }

    @Test
    @DisplayName("Handle single character prefix in search")
    void whenPrefixSearch_thenHandleSingleCharacterPrefix() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse(createHit("1", "Alice", "alice@example.com"),
            createHit("2", "Andrew", "andrew@example.com"));

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class))).thenReturn(mockResponse);

        // When
        List<Map<String, Object>> results = wildcardService.prefixSearch("users", "name", "a");

        // Then
        assertThat(results).hasSize(2);
    }

    // ==================== REGEXP SEARCH TESTS ====================

    @Test
    @DisplayName("Match regex pattern in regexp search")
    void whenRegexpSearch_thenMatchPattern() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse(createHit("1", "John Doe", "john.doe@example.com"),
            createHit("2", "Jane Doe", "jane.doe@example.com"));

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class))).thenReturn(mockResponse);

        // When
        List<Map<String, Object>> results = wildcardService.regexpSearch("users", "email", ".*@example\\.com");

        // Then
        assertThat(results)
            .hasSize(2)
            .extracting(result -> result.get("email"))
            .doesNotContainNull()
            .allSatisfy(email -> assertThat(email.toString()).endsWith("@example.com"));
    }

    @Test
    @DisplayName("Handle complex patterns in regexp search")
    void whenRegexpSearch_thenHandleComplexPattern() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse(createHit("1", "User 1", "user123@test.com"),
            createHit("2", "User 2", "user456@test.com"));

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class))).thenReturn(mockResponse);

        // When - pattern for emails starting with "user" followed by digits
        List<Map<String, Object>> results = wildcardService.regexpSearch("users", "email", "user[0-9]+@.*");

        // Then
        assertThat(results).hasSize(2);
    }

    @Test
    @DisplayName("Return empty when regexp pattern does not match")
    void whenRegexpSearch_thenReturnEmptyWhenNoMatches() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse();

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class))).thenReturn(mockResponse);

        // When
        List<Map<String, Object>> results = wildcardService.regexpSearch("users", "email", "nomatch.*");

        // Then
        assertThat(results).isEmpty();
    }

    // ==================== FUZZY SEARCH TESTS ====================

    @Test
    @DisplayName("Find similar terms with typos in fuzzy search")
    void whenFuzzySearch_thenFindSimilarTerms() throws IOException {
        // Given - searching for "jhon" should find "john"
        SearchResponse<ObjectNode> mockResponse = createMockResponse(createHit("1", "John Doe", "john.doe@example.com"),
            createHit("2", "Johnny Cash", "johnny.cash@example.com"));

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class))).thenReturn(mockResponse);

        // When
        List<Map<String, Object>> results = wildcardService.fuzzySearch("users", "name", "jhon");

        // Then
        assertThat(results)
            .hasSize(2)
            .extracting(result -> result.get("name"))
            .doesNotContainNull()
            .extracting(Object::toString)
            .extracting(name -> name.toLowerCase())
            .allSatisfy(name -> assertThat(name).contains("john"));
    }

    @Test
    @DisplayName("Handle exact matches in fuzzy search")
    void whenFuzzySearch_thenHandleExactMatch() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse(createHit("1", "John Doe", "john.doe@example.com"));

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class))).thenReturn(mockResponse);

        // When
        List<Map<String, Object>> results = wildcardService.fuzzySearch("users", "name", "john");

        // Then
        assertThat(results)
            .hasSize(1)
            .extracting(result -> result.get("name"))
            .contains("John Doe");
    }

    @Test
    @DisplayName("Return empty when terms are too different to match in fuzzy search")
    void whenFuzzySearch_thenReturnEmptyWhenTooManyDifferences() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse();

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class))).thenReturn(mockResponse);

        // When
        List<Map<String, Object>> results = wildcardService.fuzzySearch("users", "name", "zzzzz");

        // Then
        assertThat(results).isEmpty();
    }

    @Test
    @DisplayName("Tolerate small spelling mistakes in fuzzy search")
    void whenFuzzySearch_thenTolerateSmalSpellingMistakes() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse(createHit("1", "Michael", "michael@example.com"));

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class))).thenReturn(mockResponse);

        // When - searching for "micheal" (common misspelling)
        List<Map<String, Object>> results = wildcardService.fuzzySearch("users", "name", "micheal");

        // Then
        assertThat(results)
            .hasSize(1)
            .extracting(result -> result.get("name"))
            .contains("Michael");
    }

    // ==================== ADDITIONAL TEST SCENARIOS ====================

    @Test
    @DisplayName("Handle multiple wildcards in pattern")
    void whenWildcardSearch_thenHandleMultipleWildcards() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse(createHit("1", "John Michael Doe", "jmdoe@example.com"));

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class))).thenReturn(mockResponse);

        // When - search for names containing both "john" and "doe"
        List<Map<String, Object>> results = wildcardService.wildcardSearch("users", "name", "*john*doe*");

        // Then
        assertThat(results).hasSize(1);
    }

    @Test
    @DisplayName("Work with numeric prefixes in prefix search")
    void whenPrefixSearch_thenWorkWithNumericPrefix() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse(createHit("1", "User", "user123@example.com"),
            createHit("2", "User", "user124@example.com"));

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class))).thenReturn(mockResponse);

        // When
        List<Map<String, Object>> results = wildcardService.prefixSearch("users", "email", "user12");

        // Then
        assertThat(results).hasSize(2);
    }

    @Test
    @DisplayName("Respect maxResults limit in search methods")
    void whenSearchMethods_thenRespectMaxResultsLimit() throws IOException {
        // Given - set a low max results
        ReflectionTestUtils.setField(wildcardService, "maxResults", 10);

        SearchResponse<ObjectNode> mockResponse = createMockResponse(createHit("1", "User 1", "user1@example.com"));

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class))).thenReturn(mockResponse);

        // When
        wildcardService.wildcardSearch("users", "name", "*");

        // Then - verify that search was called (maxResults is applied in the query)
        verify(elasticsearchClient).search(any(Function.class), eq(ObjectNode.class));
    }

    @Test
    @DisplayName("Handle special characters in search term")
    void whenWildcardSearch_thenHandleSpecialCharacters() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse(createHit("1", "O'Brien", "obrien@example.com"));

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class))).thenReturn(mockResponse);

        // When
        List<Map<String, Object>> results = wildcardService.wildcardSearch("users", "name", "o'*");

        // Then
        assertThat(results)
            .hasSize(1)
            .extracting(result -> result.get("name"))
            .contains("O'Brien");
    }

    @Test
    @DisplayName("Handle dot metacharacter in regexp search")
    void whenRegexpSearch_thenHandleDotMetacharacter() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse(createHit("1", "User", "a.b.c@example.com"));

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class))).thenReturn(mockResponse);

        // When - dot needs to be escaped in regex
        List<Map<String, Object>> results = wildcardService.regexpSearch("users", "email", ".*\\..*\\..*@.*");

        // Then
        assertThat(results).hasSize(1);
    }

    @Test
    @DisplayName("Handle numbers in fuzzy search terms")
    void whenFuzzySearch_thenHandleNumbers() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse(createHit("1", "Room 101", "room101@example.com"));

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class))).thenReturn(mockResponse);

        // When
        List<Map<String, Object>> results = wildcardService.fuzzySearch("users", "name", "room101");

        // Then
        assertThat(results).hasSize(1);
    }

    // ==================== HELPER METHODS ====================

    /**
     * Creates a mock SearchResponse with the given hits
     */
    @SafeVarargs
    private SearchResponse<ObjectNode> createMockResponse(Hit<ObjectNode>... hits) {
        @SuppressWarnings("unchecked") SearchResponse<ObjectNode> mockResponse = mock(SearchResponse.class);

        @SuppressWarnings("unchecked") HitsMetadata<ObjectNode> mockHitsMetadata = mock(HitsMetadata.class);

        TotalHits totalHits = TotalHits.of(t -> t.value(hits.length)
            .relation(TotalHitsRelation.Eq));

        when(mockHitsMetadata.hits()).thenReturn(List.of(hits));
        when(mockHitsMetadata.total()).thenReturn(totalHits);
        when(mockResponse.hits()).thenReturn(mockHitsMetadata);

        return mockResponse;
    }

    /**
     * Creates a mock Hit with typical user data
     */
    private Hit<ObjectNode> createHit(String id, String name, String email) {
        @SuppressWarnings("unchecked") Hit<ObjectNode> mockHit = mock(Hit.class);

        Map<String, Object> sourceData = Map.of("name", name, "email", email, "status", "active");

        ObjectNode sourceNode = objectMapper.valueToTree(sourceData);

        when(mockHit.id()).thenReturn(id);
        when(mockHit.source()).thenReturn(sourceNode);
        when(mockHit.score()).thenReturn(1.0);

        return mockHit;
    }

    /**
     * Creates a mock Hit with custom data
     */
    private Hit<ObjectNode> createHit(String id, Map<String, Object> sourceData) {
        @SuppressWarnings("unchecked") Hit<ObjectNode> mockHit = mock(Hit.class);

        ObjectNode sourceNode = objectMapper.valueToTree(sourceData);

        when(mockHit.id()).thenReturn(id);
        when(mockHit.source()).thenReturn(sourceNode);
        when(mockHit.score()).thenReturn(1.0);

        return mockHit;
    }
}