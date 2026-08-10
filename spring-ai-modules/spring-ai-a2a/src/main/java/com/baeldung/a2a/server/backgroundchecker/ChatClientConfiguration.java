package com.baeldung.a2a.server.backgroundchecker;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ChatClientConfiguration {

    @Bean
    ChatClient chatClient(
        ChatClient.Builder chatClientBuilder,
        BackgroundCheckerTools backgroundCheckerTools
    ) {
        return chatClientBuilder
            .defaultSystem("""
                You are a background-check assistant for recruiters.
                Use the check-background tool to run a background check on a
                candidate using their name and email, then summarize the result.
                """)
            .defaultTools(backgroundCheckerTools)
            .build();
    }
}