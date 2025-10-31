package com.baeldung.semantic.cache;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@EnableConfigurationProperties(SemanticCacheProperties.class)
class SemanticCachingService {

    private final VectorStore vectorStore;
    private final SemanticCacheProperties semanticCacheProperties;

    SemanticCachingService(VectorStore vectorStore, SemanticCacheProperties semanticCacheProperties) {
        this.vectorStore = vectorStore;
        this.semanticCacheProperties = semanticCacheProperties;
    }

    void save(String question, String answer) {
        Document document = Document
            .builder()
            .text(question)
            .metadata(semanticCacheProperties.metadataField(), answer)
            .build();
        vectorStore.add(List.of(document));
    }

    Optional<String> search(String question) {
        SearchRequest searchRequest = SearchRequest.builder()
            .query(question)
            .similarityThreshold(semanticCacheProperties.similarityThreshold())
            .topK(1)
            .build();
        List<Document> results = vectorStore.similaritySearch(searchRequest);

        if (results.isEmpty()) {
            return Optional.empty();
        }

        Document result = results.getFirst();
        String answer = String.valueOf(result.getMetadata().get(semanticCacheProperties.metadataField()));
        return Optional.of(answer);
    }

}