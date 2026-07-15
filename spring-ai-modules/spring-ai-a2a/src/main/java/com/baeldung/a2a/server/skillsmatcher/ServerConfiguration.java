package com.baeldung.a2a.server.skillsmatcher;

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
        return new DefaultAgentExecutor(chatClient, (chat, ctx) -> {
            String userMessage = DefaultAgentExecutor.extractTextFromMessage(ctx.getMessage());
            return chat.prompt().user(userMessage).call().content();
        });
    }

    @Bean
    AgentCard agentCard(@Value("${server.host}") String host, @Value("${server.port}") int port) {
        return new AgentCard.Builder()
            .name("Skills Matcher Agent")
            .description("Evaluates how well a candidate's skills match a job's required skills")
            .url("http://" + host + ":" + port + "/a2a/")
            .version("1.0.0")
            .capabilities(new AgentCapabilities.Builder().streaming(false).build())
            .defaultInputModes(List.of("text"))
            .defaultOutputModes(List.of("text"))
            .skills(List.of(new AgentSkill.Builder()
                .id("skills_matching")
                .name("Skills Matching")
                .description("Compares candidate skills to job requirements and scores the fit")
                .tags(List.of("hiring", "recruiting"))
                .build()))
            .protocolVersion("0.3.0")
            .build();
    }
}