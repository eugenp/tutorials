package com.baeldung.a2a.server.salaryevaluator;

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
            .name("Salary Evaluator Agent")
            .description("Checks if a candidate's expected salary fits a job title's budget range")
            .url(String.format("http://%s:%d/", host, port))
            .version("1.0.0")
            .capabilities(new AgentCapabilities
                .Builder()
                .streaming(false)
                .build())
            .defaultInputModes(List.of("text"))
            .defaultOutputModes(List.of("text"))
            .skills(List.of(new AgentSkill.Builder()
                .id("salary_evaluation")
                .name("Salary Evaluation")
                .description("Compares a candidate's expected salary against a job title's budget range")
                .tags(List.of("hiring", "recruiting", "compensation"))
                .build()))
            .protocolVersion("1.0.1")
            .build();
    }
}