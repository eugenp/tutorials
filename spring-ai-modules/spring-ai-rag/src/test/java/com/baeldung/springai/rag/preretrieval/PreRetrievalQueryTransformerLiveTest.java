package com.baeldung.springai.rag.preretrieval;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.preretrieval.query.transformation.CompressionQueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.QueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.TranslationQueryTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PreRetrievalQueryTransformerLiveTest {

    private static final Logger logger = LoggerFactory.getLogger(PreRetrievalQueryTransformerLiveTest.class);

    @Autowired
    private ChatClient.Builder chatClientBuilder;

    @Test
    void whenQueryIsVerbose_thenRewrite() {
        QueryTransformer queryTransformer = RewriteQueryTransformer.builder()
            .chatClientBuilder(chatClientBuilder)
            .build();

        String userQuery = "I'm studying machine learning and honestly the whole thing is confusing. What is an LLM anyway?";
        Query transformed = queryTransformer.transform(new Query(userQuery));

        logger.info("Rewrite: [{}] -> [{}]", userQuery, transformed.text());
        assertThat(transformed.text()).isNotEqualTo(userQuery);
    }

    @Test
    void whenQueryIsInTraditionalChinese_thenTranslate() {
        QueryTransformer queryTransformer = TranslationQueryTransformer.builder()
            .chatClientBuilder(chatClientBuilder)
            .targetLanguage("english")
            .build();

        String userQuery = "什麼是向量資料庫？";
        Query transformed = queryTransformer.transform(new Query(userQuery));

        logger.info("Translation: [{}] -> [{}]", userQuery, transformed.text());
        assertThat(transformed.text()).isNotEqualTo(userQuery);
    }

    @Test
    void whenQueryIsFollowUpWithHistory_thenCompress() {
        QueryTransformer queryTransformer = CompressionQueryTransformer.builder()
            .chatClientBuilder(chatClientBuilder)
            .build();

        List<Message> history = List.of(
            new UserMessage("What is Spring AI?"),
            new AssistantMessage("Spring AI is an application framework that brings Spring's design principles to AI engineering."));
        String followUpQuery = "And how does it support RAG?";
        Query query = Query.builder()
            .text(followUpQuery)
            .history(history)
            .build();

        Query transformed = queryTransformer.transform(query);

        logger.info("Compression: [{}] -> [{}]", followUpQuery, transformed.text());
        assertThat(transformed.text()).isNotEqualTo(followUpQuery);
    }
}
