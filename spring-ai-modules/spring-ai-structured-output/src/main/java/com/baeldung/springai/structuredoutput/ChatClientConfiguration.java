package com.baeldung.springai.structuredoutput;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.StructuredOutputValidationAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import tools.jackson.databind.json.JsonMapper;

@Configuration
class ChatClientConfiguration {

    @Bean
    @Primary
    ChatClient chatClient(ChatModel chatModel) {
        return ChatClient
            .builder(chatModel)
            .build();
    }

    @Bean
    ChatClient outputValidatingChatClient(ChatModel chatModel) {
        return ChatClient
            .builder(chatModel)
            .defaultAdvisors(StructuredOutputValidationAdvisor.builder()
                .maxRepeatAttempts(5)
                .outputType(Recipe.class)
                .jsonMapper(JsonMapper.builder().build())
                .build())
            .build();
    }
}
