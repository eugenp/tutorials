package com.baeldung.springai.anthropic;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class ChatBotConfiguration {

    @Bean
    public ChatClient chatClient(
        @Qualifier("anthropicChatModel") ChatModel chatModel,
        @Value("classpath:prompts/chatbot-system-prompt.st") Resource systemPrompt
    ) {
        return ChatClient
            .builder(chatModel)
            .defaultSystem(systemPrompt)
            .build();
    }

}