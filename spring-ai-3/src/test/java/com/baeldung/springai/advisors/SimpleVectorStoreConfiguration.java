package com.baeldung.springai.advisors;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleVectorStoreConfiguration {

    @Bean
    public VectorStore vectorStore(@Qualifier("openAiEmbeddingModel")EmbeddingModel embeddingModel) {
    	
    	return CustomSimpleVectorStore.builder(embeddingModel)
    	  .build();
    }
}
