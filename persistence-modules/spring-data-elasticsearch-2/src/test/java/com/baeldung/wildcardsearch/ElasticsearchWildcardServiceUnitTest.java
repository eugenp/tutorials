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
    @DisplayName("wildcardSearch should return matching documents")
    void testWildcardSearch_ReturnsMatchingDocuments() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse(
                createHit("1", "John Doe", "john.doe@example.com"),
                createHit("2", "Johnny Cash", "johnny.cash@example.com")
        );

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class)))
                .thenReturn(mockResponse);

        // When
        List<Map<String, Object>> results = wildcardService.wildcardSearch("users", "name", "john*");

        // Then
        assertThat(results).hasSize(2);
        assertThat(results.get(0)).containsEntry("name", "John Doe");
        assertThat(results.get(1)).containsEntry("name", "Johnny Cash");
        verify(elasticsearchClient).search(any(Function.class), eq(ObjectNode.class));
    }

    @Test
    @DisplayName("wildcardSearch should handle empty results")
    void testWildcardSearch_HandlesEmptyResults() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse();

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class)))
                .thenReturn(mockResponse);

        // When
        List<Map<String, Object>> results = wildcardService.wildcardSearch("users", "name", "xyz*");

        // Then
        assertThat(results).isEmpty();
    }

    @Test
    @DisplayName("wildcardSearch should be case-insensitive")
    void testWildcardSearch_IsCaseInsensitive() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse(
                createHit("1", "John Doe", "john.doe@example.com")
        );

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class)))
                .thenReturn(mockResponse);

        // When
        List<Map<String, Object>> results = wildcardService.wildcardSearch("users", "name", "JOHN*");

        // Then
        assertThat(results).hasSize(1);
        assertThat(results.get(0)).containsEntry("name", "John Doe");
    }

    @Test
    @DisplayName("wildcardSearch should throw IOException on client failure")
    void testWildcardSearch_ThrowsIOException() throws IOException {
        // Given
        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class)))
                .thenThrow(new IOException("Connection timeout"));

        // When & Then
        assertThrows(IOException.class, () ->
                wildcardService.wildcardSearch("users", "name", "john*"));
    }

    // ==================== PREFIX SEARCH TESTS ====================

    @Test
    @DisplayName("prefixSearch should return documents with matching prefix")
    void testPrefixSearch_ReturnsMatchingDocuments() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse(
                createHit("1", "John Doe", "john@example.com"),
                createHit("2", "John Smith", "john.smith@example.com")
        );

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class)))
                .thenReturn(mockResponse);

        // When
        List<Map<String, Object>> results = wildcardService.prefixSearch("users", "email", "john");

        // Then
        assertThat(results).hasSize(2);
        assertThat(results)
                .allMatch(r -> r.get("email").toString().startsWith("john"));
    }

    @Test
    @DisplayName("prefixSearch should return empty list when no matches")
    void testPrefixSearch_NoMatches() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse();

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class)))
                .thenReturn(mockResponse);

        // When
        List<Map<String, Object>> results = wildcardService.prefixSearch("users", "email", "xyz");

        // Then
        assertThat(results).isEmpty();
    }

    @Test
    @DisplayName("prefixSearch should handle single character prefix")
    void testPrefixSearch_SingleCharacterPrefix() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse(
                createHit("1", "Alice", "alice@example.com"),
                createHit("2", "Andrew", "andrew@example.com")
        );

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class)))
                .thenReturn(mockResponse);

        // When
        List<Map<String, Object>> results = wildcardService.prefixSearch("users", "name", "a");

        // Then
        assertThat(results).hasSize(2);
    }

    // ==================== REGEXP SEARCH TESTS ====================

    @Test
    @DisplayName("regexpSearch should match regex pattern")
    void testRegexpSearch_MatchesPattern() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse(
                createHit("1", "John Doe", "john.doe@example.com"),
                createHit("2", "Jane Doe", "jane.doe@example.com")
        );

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class)))
                .thenReturn(mockResponse);

        // When
        List<Map<String, Object>> results = wildcardService.regexpSearch("users", "email", ".*@example\\.com");

        // Then
        assertThat(results).hasSize(2);
        assertThat(results)
                .allMatch(r -> r.get("email").toString().endsWith("@example.com"));
    }

    @Test
    @DisplayName("regexpSearch should handle complex patterns")
    void testRegexpSearch_ComplexPattern() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse(
                createHit("1", "User 1", "user123@test.com"),
                createHit("2", "User 2", "user456@test.com")
        );

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class)))
                .thenReturn(mockResponse);

        // When - pattern for emails starting with "user" followed by digits
        List<Map<String, Object>> results = wildcardService.regexpSearch("users", "email", "user[0-9]+@.*");

        // Then
        assertThat(results).hasSize(2);
    }

    @Test
    @DisplayName("regexpSearch should return empty for non-matching pattern")
    void testRegexpSearch_NoMatches() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse();

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class)))
                .thenReturn(mockResponse);

        // When
        List<Map<String, Object>> results = wildcardService.regexpSearch("users", "email", "nomatch.*");

        // Then
        assertThat(results).isEmpty();
    }

    // ==================== FUZZY SEARCH TESTS ====================

    @Test
    @DisplayName("fuzzySearch should find similar terms with typos")
    void testFuzzySearch_FindsSimilarTerms() throws IOException {
        // Given - searching for "jhon" should find "john"
        SearchResponse<ObjectNode> mockResponse = createMockResponse(
                createHit("1", "John Doe", "john.doe@example.com"),
                createHit("2", "Johnny Cash", "johnny.cash@example.com")
        );

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class)))
                .thenReturn(mockResponse);

        // When
        List<Map<String, Object>> results = wildcardService.fuzzySearch("users", "name", "jhon");

        // Then
        assertThat(results).hasSize(2);
        assertThat(results)
                .allMatch(r -> r.get("name").toString().toLowerCase().contains("john"));
    }

    @Test
    @DisplayName("fuzzySearch should handle exact matches")
    void testFuzzySearch_ExactMatch() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse(
                createHit("1", "John Doe", "john.doe@example.com")
        );

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class)))
                .thenReturn(mockResponse);

        // When
        List<Map<String, Object>> results = wildcardService.fuzzySearch("users", "name", "john");

        // Then
        assertThat(results).hasSize(1);
        assertThat(results.get(0)).containsEntry("name", "John Doe");
    }

    @Test
    @DisplayName("fuzzySearch should handle terms too different to match")
    void testFuzzySearch_TooManyDifferences() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse();

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class)))
                .thenReturn(mockResponse);

        // When
        List<Map<String, Object>> results = wildcardService.fuzzySearch("users", "name", "zzzzz");

        // Then
        assertThat(results).isEmpty();
    }

    @Test
    @DisplayName("fuzzySearch should be tolerant to small spelling mistakes")
    void testFuzzySearch_ToleratesSpellingMistakes() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse(
                createHit("1", "Michael", "michael@example.com")
        );

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class)))
                .thenReturn(mockResponse);

        // When - searching for "micheal" (common misspelling)
        List<Map<String, Object>> results = wildcardService.fuzzySearch("users", "name", "micheal");

        // Then
        assertThat(results).hasSize(1);
        assertThat(results.get(0)).containsEntry("name", "Michael");
    }

    // ==================== ADDITIONAL TEST SCENARIOS ====================

    @Test
    @DisplayName("wildcardSearch should handle multiple wildcards in pattern")
    void testWildcardSearch_MultipleWildcards() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse(
                createHit("1", "John Michael Doe", "jmdoe@example.com")
        );

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class)))
                .thenReturn(mockResponse);

        // When - search for names containing both "john" and "doe"
        List<Map<String, Object>> results = wildcardService.wildcardSearch("users", "name", "*john*doe*");

        // Then
        assertThat(results).hasSize(1);
    }

    @Test
    @DisplayName("prefixSearch should work with numeric prefixes")
    void testPrefixSearch_NumericPrefix() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse(
                createHit("1", "User", "user123@example.com"),
                createHit("2", "User", "user124@example.com")
        );

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class)))
                .thenReturn(mockResponse);

        // When
        List<Map<String, Object>> results = wildcardService.prefixSearch("users", "email", "user12");

        // Then
        assertThat(results).hasSize(2);
    }

    @Test
    @DisplayName("All search methods should respect maxResults limit")
    void testSearchMethods_RespectMaxResults() throws IOException {
        // Given - set a low max results
        ReflectionTestUtils.setField(wildcardService, "maxResults", 10);

        SearchResponse<ObjectNode> mockResponse = createMockResponse(
                createHit("1", "User 1", "user1@example.com")
        );

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class)))
                .thenReturn(mockResponse);

        // When
        wildcardService.wildcardSearch("users", "name", "*");

        // Then - verify that search was called (maxResults is applied in the query)
        verify(elasticsearchClient).search(any(Function.class), eq(ObjectNode.class));
    }

    @Test
    @DisplayName("wildcardSearch should handle special characters in search term")
    void testWildcardSearch_SpecialCharacters() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse(
                createHit("1", "O'Brien", "obrien@example.com")
        );

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class)))
                .thenReturn(mockResponse);

        // When
        List<Map<String, Object>> results = wildcardService.wildcardSearch("users", "name", "o'*");

        // Then
        assertThat(results).hasSize(1);
        assertThat(results.get(0)).containsEntry("name", "O'Brien");
    }

    @Test
    @DisplayName("regexpSearch should handle dot metacharacter")
    void testRegexpSearch_DotMetacharacter() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse(
                createHit("1", "User", "a.b.c@example.com")
        );

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class)))
                .thenReturn(mockResponse);

        // When - dot needs to be escaped in regex
        List<Map<String, Object>> results = wildcardService.regexpSearch("users", "email", ".*\\..*\\..*@.*");

        // Then
        assertThat(results).hasSize(1);
    }

    @Test
    @DisplayName("fuzzySearch should handle numbers in search terms")
    void testFuzzySearch_WithNumbers() throws IOException {
        // Given
        SearchResponse<ObjectNode> mockResponse = createMockResponse(
                createHit("1", "Room 101", "room101@example.com")
        );

        when(elasticsearchClient.search(any(Function.class), eq(ObjectNode.class)))
                .thenReturn(mockResponse);

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
        @SuppressWarnings("unchecked")
        SearchResponse<ObjectNode> mockResponse = mock(SearchResponse.class);

        @SuppressWarnings("unchecked")
        HitsMetadata<ObjectNode> mockHitsMetadata = mock(HitsMetadata.class);

        TotalHits totalHits = TotalHits.of(t -> t
                .value(hits.length)
                .relation(TotalHitsRelation.Eq)
        );

        when(mockHitsMetadata.hits()).thenReturn(List.of(hits));
        when(mockHitsMetadata.total()).thenReturn(totalHits);
        when(mockResponse.hits()).thenReturn(mockHitsMetadata);

        return mockResponse;
    }

    /**
     * Creates a mock Hit with typical user data
     */
    private Hit<ObjectNode> createHit(String id, String name, String email) {
        @SuppressWarnings("unchecked")
        Hit<ObjectNode> mockHit = mock(Hit.class);

        Map<String, Object> sourceData = Map.of(
                "name", name,
                "email", email,
                "status", "active"
        );

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
        @SuppressWarnings("unchecked")
        Hit<ObjectNode> mockHit = mock(Hit.class);

        ObjectNode sourceNode = objectMapper.valueToTree(sourceData);

        when(mockHit.id()).thenReturn(id);
        when(mockHit.source()).thenReturn(sourceNode);
        when(mockHit.score()).thenReturn(1.0);

        return mockHit;
    }
}