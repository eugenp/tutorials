package com.baeldung.springai.vectorstore.oracle;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
class RAGChatbotConfiguration {

    private static final int MAX_RESULTS = 10;

    @Bean
    PromptTemplate promptTemplate(
        @Value("classpath:prompt-template.st") Resource promptTemplate
    ) throws IOException {
        String template = promptTemplate.getContentAsString(StandardCharsets.UTF_8);
        return PromptTemplate
            .builder()
            .renderer(StTemplateRenderer
                .builder()
                .startDelimiterToken('<')
                .endDelimiterToken('>')
                .build())
            .template(template)
            .build();
    }

    @Bean
    ChatClient chatClient(
        ChatModel chatModel,
        VectorStore vectorStore,
        PromptTemplate promptTemplate
    ) {
        return ChatClient
            .builder(chatModel)
            .defaultAdvisors(
                QuestionAnswerAdvisor
                    .builder(vectorStore)
                    .promptTemplate(promptTemplate)
                    .searchRequest(SearchRequest
                        .builder()
                        .topK(MAX_RESULTS)
                        .build())
                    .build()
            )
            .build();
    }

}