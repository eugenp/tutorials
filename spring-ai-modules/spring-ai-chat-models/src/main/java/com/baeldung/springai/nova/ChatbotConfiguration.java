package com.baeldung.springai.nova;

import java.util.function.Function;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.core.io.Resource;

@Configuration
public class ChatbotConfiguration {

    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }

    @Bean
    @Description("Get Baeldung author details using an article title")
    public Function<AuthorFetcher.Query, AuthorFetcher.Author> getAuthor() {
        return new AuthorFetcher();
    }

    @Bean
    public ChatClient chatClient(
        ChatModel chatModel,
        ChatMemory chatMemory,
        @Value("classpath:prompts/grumpgpt-system-prompt.st") Resource systemPrompt
    ) {
        return ChatClient
            .builder(chatModel)
            .defaultFunctions("getAuthor")
            .defaultSystem(systemPrompt)
            .defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory))
            .build();
    }

}