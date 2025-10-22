package com.baeldung.wildcardsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ElasticsearchWildcardService {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchWildcardService.class);

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Value("${elasticsearch.max-results:1000}")
    private int maxResults;

    /**
     * Performs wildcard search using the new Java API Client
     * Note: For case-insensitive search, the searchTerm is converted to lowercase
     * and the field should be mapped with a .keyword subfield or use caseInsensitive flag
     */
    public List<Map<String, Object>> wildcardSearch(String indexName, String fieldName, String searchTerm) throws IOException {
        logger.info("Performing wildcard search on index: {}, field: {}, term: {}", indexName, fieldName, searchTerm);

        // Convert search term to lowercase for case-insensitive search
        String lowercaseSearchTerm = searchTerm.toLowerCase();

        SearchResponse<ObjectNode> response = elasticsearchClient.search(s -> s.index(indexName).query(q -> q.wildcard(w -> w.field(fieldName).value(lowercaseSearchTerm).caseInsensitive(true))).size(maxResults), ObjectNode.class);

        return extractSearchResults(response);
    }

    public List<Map<String, Object>> wildcardSearchOnKeyword(String indexName, String fieldName, String searchTerm) throws IOException {
        logger.info("Performing wildcard search on keyword field - index: {}, field: {}, term: {}", indexName, fieldName, searchTerm);

        // Use the .keyword subfield for exact matching
        String keywordField = fieldName + ".keyword";

        // Convert to lowercase for case-insensitive matching
        String lowercaseSearchTerm = searchTerm.toLowerCase();

        SearchResponse<ObjectNode> response = elasticsearchClient.search(s -> s.index(indexName).query(q -> q.wildcard(w -> w.field(keywordField).value(lowercaseSearchTerm).caseInsensitive(true))).size(maxResults), ObjectNode.class);

        return extractSearchResults(response);
    }

    /**
     * Performs prefix search - optimized for autocomplete
     */
    public List<Map<String, Object>> prefixSearch(String indexName, String fieldName, String prefix) throws IOException {
        logger.info("Performing prefix search on index: {}, field: {}, prefix: {}", indexName, fieldName, prefix);

        SearchResponse<ObjectNode> response = elasticsearchClient.search(s -> s.index(indexName).query(q -> q.prefix(p -> p.field(fieldName).value(prefix))).size(maxResults), ObjectNode.class);

        return extractSearchResults(response);
    }

    /**
     * Performs regex search for complex pattern matching
     */
    public List<Map<String, Object>> regexpSearch(String indexName, String fieldName, String pattern) throws IOException {
        logger.info("Performing regexp search on index: {}, field: {}, pattern: {}", indexName, fieldName, pattern);

        SearchResponse<ObjectNode> response = elasticsearchClient.search(s -> s.index(indexName).query(q -> q.regexp(r -> r.field(fieldName).value(pattern))).size(maxResults), ObjectNode.class);

        return extractSearchResults(response);
    }

    /**
     * Performs fuzzy search for typo-tolerant searching
     */
    public List<Map<String, Object>> fuzzySearch(String indexName, String fieldName, String searchTerm) throws IOException {
        logger.info("Performing fuzzy search on index: {}, field: {}, term: {}", indexName, fieldName, searchTerm);

        SearchResponse<ObjectNode> response = elasticsearchClient.search(s -> s.index(indexName).query(q -> q.fuzzy(f -> f.field(fieldName).value(searchTerm).fuzziness("AUTO"))).size(maxResults), ObjectNode.class);

        return extractSearchResults(response);
    }

    /**
     * Performs match phrase prefix search - good for autocomplete
     */
    public List<Map<String, Object>> matchPhrasePrefixSearch(String indexName, String fieldName, String searchTerm) throws IOException {
        logger.info("Performing match phrase prefix search on index: {}, field: {}, term: {}", indexName, fieldName, searchTerm);

        SearchResponse<ObjectNode> response = elasticsearchClient.search(s -> s.index(indexName).query(q -> q.matchPhrasePrefix(m -> m.field(fieldName).query(searchTerm))).size(maxResults), ObjectNode.class);

        return extractSearchResults(response);
    }

    /**
     * Combined wildcard search with multiple conditions
     */
    public List<Map<String, Object>> combinedWildcardSearch(String indexName, String field1, String wildcard1, String field2, String wildcard2) throws IOException {
        logger.info("Performing combined wildcard search on index: {}", indexName);

        SearchResponse<ObjectNode> response = elasticsearchClient.search(s -> s.index(indexName).query(q -> q.bool(b -> b.must(m -> m.wildcard(w -> w.field(field1).value(wildcard1))).must(m -> m.wildcard(w -> w.field(field2).value(wildcard2))))).size(maxResults), ObjectNode.class);

        return extractSearchResults(response);
    }

    /**
     * Case-insensitive wildcard search
     */
    public List<Map<String, Object>> caseInsensitiveWildcardSearch(String indexName, String fieldName, String searchTerm) throws IOException {
        logger.info("Performing case-insensitive wildcard search on index: {}, field: {}, term: {}", indexName, fieldName, searchTerm);

        String lowercaseSearchTerm = searchTerm.toLowerCase();

        SearchResponse<ObjectNode> response = elasticsearchClient.search(s -> s.index(indexName).query(q -> q.wildcard(w -> w.field(fieldName + ".lowercase").value(lowercaseSearchTerm).caseInsensitive(true))).size(maxResults), ObjectNode.class);

        return extractSearchResults(response);
    }

    /**
     * Advanced wildcard search with filtering and sorting
     */
    public List<Map<String, Object>> advancedWildcardSearch(String indexName, String wildcardField, String wildcardTerm, String filterField, String filterValue, String sortField) throws IOException {
        logger.info("Performing advanced wildcard search on index: {}", indexName);

        SearchResponse<ObjectNode> response = elasticsearchClient.search(s -> s.index(indexName).query(q -> q.bool(b -> b.must(m -> m.wildcard(w -> w.field(wildcardField).value(wildcardTerm))).filter(f -> f.term(t -> t.field(filterField).value(filterValue))))).sort(so -> so.field(f -> f.field(sortField).order(SortOrder.Asc))).size(maxResults), ObjectNode.class);

        return extractSearchResults(response);
    }

    /**
     * Multi-field wildcard search
     */
    public List<Map<String, Object>> multiFieldWildcardSearch(String indexName, String searchTerm, String... fields) throws IOException {
        logger.info("Performing multi-field wildcard search on index: {}, fields: {}", indexName, String.join(", ", fields));

        SearchResponse<ObjectNode> response = elasticsearchClient.search(s -> s.index(indexName).query(q -> q.multiMatch(m -> m.query(searchTerm).fields(List.of(fields)).type(TextQueryType.PhrasePrefix))).size(maxResults), ObjectNode.class);

        return extractSearchResults(response);
    }

    /**
     * Extract results from SearchResponse
     */
    private List<Map<String, Object>> extractSearchResults(SearchResponse<ObjectNode> response) {
        List<Map<String, Object>> results = new ArrayList<>();

        logger.info("Search completed. Total hits: {}", response.hits().total().value());

        for (Hit<ObjectNode> hit : response.hits().hits()) {
            Map<String, Object> sourceMap = new HashMap<>();

            if (hit.source() != null) {
                hit.source().fields().forEachRemaining(entry -> {
                    // Extract the actual value from JsonNode
                    Object value = extractJsonNodeValue(entry.getValue());
                    sourceMap.put(entry.getKey(), value);
                });
            }

            results.add(sourceMap);
        }

        return results;
    }

    /**
     * Helper method to extract actual values from JsonNode objects
     */
    private Object extractJsonNodeValue(com.fasterxml.jackson.databind.JsonNode jsonNode) {
        if (jsonNode == null || jsonNode.isNull()) {
            return null;
        } else if (jsonNode.isTextual()) {
            return jsonNode.asText();
        } else if (jsonNode.isInt()) {
            return jsonNode.asInt();
        } else if (jsonNode.isLong()) {
            return jsonNode.asLong();
        } else if (jsonNode.isDouble() || jsonNode.isFloat()) {
            return jsonNode.asDouble();
        } else if (jsonNode.isBoolean()) {
            return jsonNode.asBoolean();
        } else if (jsonNode.isArray()) {
            List<Object> list = new ArrayList<>();
            jsonNode.elements().forEachRemaining(element -> list.add(extractJsonNodeValue(element)));
            return list;
        } else if (jsonNode.isObject()) {
            Map<String, Object> map = new HashMap<>();
            jsonNode.fields().forEachRemaining(entry -> map.put(entry.getKey(), extractJsonNodeValue(entry.getValue())));
            return map;
        } else {
            return jsonNode.asText();
        }
    }
}