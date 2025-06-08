package com.baeldung.springai.advisors;

import java.util.Comparator;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.data.util.Pair;

public class CustomSimpleVectorStore extends SimpleVectorStore {
	
	protected CustomSimpleVectorStore(SimpleVectorStoreBuilder builder) {
		super(builder);
	}

	@Override
	public List<Document> doSimilaritySearch(SearchRequest request) {
		
		float[] userQueryEmbedding = embeddingModel.embed(request.getQuery());
        return this.store.values()
          .stream()
          .map(entry -> Pair.of(entry.getId(),
            EmbeddingMath.cosineSimilarity(userQueryEmbedding, entry.getEmbedding())))
          .filter(s -> s.getSecond() >= request.getSimilarityThreshold())
          .sorted(Comparator.comparing(Pair::getSecond))
          .limit(request.getTopK())
          .map(s -> this.store.get(s.getFirst()))
          .map(content -> content
				.toDocument(EmbeddingMath.cosineSimilarity(userQueryEmbedding, content.getEmbedding())))
          .toList();
	}
}
