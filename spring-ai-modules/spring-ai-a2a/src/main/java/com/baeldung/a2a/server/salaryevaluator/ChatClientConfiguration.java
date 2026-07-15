package com.baeldung.a2a.server.salaryevaluator;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ChatClientConfiguration {

    @Bean
    ChatClient chatClient(ChatClient.Builder chatClientBuilder, SalaryEvaluatorTools salaryEvaluatorTools) {
        return chatClientBuilder
            .defaultSystem("""
                You are a salary-evaluation assistant for recruiters.
                Use the evaluate-salary tool to compare a candidate's expected
                salary against the job title they've applied for.""")
            .defaultTools(salaryEvaluatorTools)
            .build();
    }
}