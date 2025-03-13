package com.baeldung.chatbot.mongodb.configuration;

import com.baeldung.chatbot.mongodb.assistants.ArticleBasedAssistant;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.CreateCollectionOptions;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.mongodb.IndexMapping;
import dev.langchain4j.store.embedding.mongodb.MongoDbEmbeddingStore;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;

import static dev.langchain4j.model.openai.OpenAiEmbeddingModelName.TEXT_EMBEDDING_3_SMALL;

@Configuration
public class ChatBotConfiguration {

    @Value("${app.mongodb.url}")
    private String mongodbUrl;

    @Value("${app.mongodb.db-name}")
    private String databaseName;

    @Value("${app.openai.apiKey}")
    private String apiKey;


    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(mongodbUrl);
    }

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore(MongoClient mongoClient) {
        String collectionName = "embeddings";
        String indexName = "embedding";
        Long maxResultRatio = 10L;
        CreateCollectionOptions createCollectionOptions = new CreateCollectionOptions();
        Bson filter = null;
        IndexMapping indexMapping = IndexMapping.builder()
          .dimension(TEXT_EMBEDDING_3_SMALL.dimension())
          .metadataFieldNames(new HashSet<>())
          .build();
        Boolean createIndex = true;

        return new MongoDbEmbeddingStore(
          mongoClient,
          databaseName,
          collectionName,
          indexName,
          maxResultRatio,
          createCollectionOptions,
          filter,
          indexMapping,
          createIndex
        );
    }

    @Bean
    public EmbeddingModel embeddingModel() {
        return OpenAiEmbeddingModel.builder()
          .apiKey(apiKey)
          .modelName(TEXT_EMBEDDING_3_SMALL)
          .build();
    }

    @Bean
    public ContentRetriever contentRetriever(EmbeddingStore<TextSegment> embeddingStore, EmbeddingModel embeddingModel) {
        return EmbeddingStoreContentRetriever.builder()
          .embeddingStore(embeddingStore)
          .embeddingModel(embeddingModel)
          .maxResults(10)
          .minScore(0.8)
          .build();
    }

    @Bean
    public ChatLanguageModel chatModel() {
        return OpenAiChatModel.builder()
          .apiKey(apiKey)
          .modelName("gpt-4o-mini")
          .build();
    }

    @Bean
    public ArticleBasedAssistant articleBasedAssistant(ChatLanguageModel chatModel, ContentRetriever contentRetriever) {
        return AiServices.builder(ArticleBasedAssistant.class)
          .chatLanguageModel(chatModel)
          .contentRetriever(contentRetriever)
          .build();
    }
}
