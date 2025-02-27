package com.baeldung.springai.deepseek;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ChatbotConfiguration {

    @Bean
    ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }

    /**
     * When using alternate providers, use the following qualifiers instead:
     * - @Qualifier("bedrockProxyChatModel") for Amazon Bedrock Converse API
     * - @Qualifier("ollamaChatModel") for Ollama
     */
    @Bean
    ChatClient chatClient(
        @Qualifier("openAiChatModel") ChatModel chatModel,
        ChatMemory chatMemory
    ) {
        return ChatClient
            .builder(chatModel)
            .defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory))
            .build();
    }

}