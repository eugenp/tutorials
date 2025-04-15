package com.baeldung.springai.rag.mongodb.config;

import org.springframework.ai.autoconfigure.vectorstore.mongo.MongoDBAtlasVectorStoreProperties;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.mongodb.atlas.MongoDBAtlasVectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class VectorStoreConfig {
    @Bean
    public MongoDBAtlasVectorStore vectorStore(MongoTemplate mongoTemplate,
      @Qualifier("openAiEmbeddingModel") EmbeddingModel embeddingModel,
      MongoDBAtlasVectorStoreProperties properties) {
 
      return MongoDBAtlasVectorStore.builder(mongoTemplate, embeddingModel)
        .collectionName(properties.getCollectionName())
        .pathName(properties.getPathName())
        .vectorIndexName(properties.getIndexName())
        .initializeSchema(properties.isInitializeSchema())
        .build();
    }
}
