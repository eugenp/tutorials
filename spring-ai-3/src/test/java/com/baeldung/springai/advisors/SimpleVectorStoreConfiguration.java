package com.baeldung.springai.advisors;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.util.Pair;

import java.util.Comparator;
import java.util.List;

@Configuration
public class SimpleVectorStoreConfiguration {

    @Bean
    public VectorStore vectorStore(@Qualifier("openAiEmbeddingModel")EmbeddingModel embeddingModel) {
        return new SimpleVectorStore(embeddingModel) {
            @Override
            public List<Document> doSimilaritySearch(SearchRequest request) {
                float[] userQueryEmbedding = embeddingModel.embed(request.query);
                return this.store.values()
                  .stream()
                  .map(entry -> Pair.of(entry.getId(),
                    EmbeddingMath.cosineSimilarity(userQueryEmbedding, entry.getEmbedding())))
                  .filter(s -> s.getSecond() >= request.getSimilarityThreshold())
                  .sorted(Comparator.comparing(Pair::getSecond))
                  .limit(request.getTopK())
                  .map(s -> this.store.get(s.getFirst()))
                  .toList();
            }
        };
    }
}
