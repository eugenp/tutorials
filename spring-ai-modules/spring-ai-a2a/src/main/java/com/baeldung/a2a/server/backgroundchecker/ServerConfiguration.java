package com.baeldung.a2a.server.backgroundchecker;

import io.a2a.server.agentexecution.AgentExecutor;
import io.a2a.spec.AgentCapabilities;
import io.a2a.spec.AgentCard;
import io.a2a.spec.AgentSkill;
import org.springaicommunity.a2a.server.executor.DefaultAgentExecutor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class ServerConfiguration {

    @Bean
    AgentExecutor agentExecutor(ChatClient chatClient) {
        return new DefaultAgentExecutor(chatClient, (client, requestContext) -> {
            String userMessage = DefaultAgentExecutor.extractTextFromMessage(requestContext.getMessage());
            return client
                .prompt()
                .user(userMessage)
                .call()
                .content();
        });
    }

    @Bean
    AgentCard agentCard(
        @Value("${server.host}") String host,
        @Value("${server.port}") int port
    ) {
        return new AgentCard.Builder()
            .name("Background Check Agent")
            .description("Runs a background check on a candidate")
            .url(String.format("http://%s:%d/a2a/", host, port))
            .version("1.0.0")
            .capabilities(new AgentCapabilities
                .Builder()
                .streaming(false)
                .build())
            .defaultInputModes(List.of("text"))
            .defaultOutputModes(List.of("text"))
            .skills(List.of(new AgentSkill.Builder()
                .id("background_check")
                .name("Background Check")
                .description("Runs a background check on a candidate using their name and email")
                .tags(List.of("hiring", "recruiting", "compliance"))
                .build()))
            .protocolVersion("0.3.0")
            .build();
    }
}