package com.baeldung.springai.rag.mongodb.config;

import org.springframework.ai.autoconfigure.vectorstore.mongo.MongoDBAtlasVectorStoreProperties;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.MongoDBAtlasVectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.StringUtils;

@Configuration
public class VectorStoreConfig {
    @Bean
    public MongoDBAtlasVectorStore vectorStore(MongoTemplate mongoTemplate,
      @Qualifier("openAiEmbeddingModel") EmbeddingModel embeddingModel,
      MongoDBAtlasVectorStoreProperties properties) {
        MongoDBAtlasVectorStore.MongoDBVectorStoreConfig.Builder builder = MongoDBAtlasVectorStore.MongoDBVectorStoreConfig.builder();
        if (StringUtils.hasText(properties.getCollectionName())) {
            builder.withCollectionName(properties.getCollectionName());
        }

        if (StringUtils.hasText(properties.getPathName())) {
            builder.withPathName(properties.getPathName());
        }

        if (StringUtils.hasText(properties.getIndexName())) {
            builder.withVectorIndexName(properties.getIndexName());
        }

        MongoDBAtlasVectorStore.MongoDBVectorStoreConfig config = builder.build();
        return new MongoDBAtlasVectorStore(mongoTemplate, embeddingModel, config, properties.isInitializeSchema());
    }
}
