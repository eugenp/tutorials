package com.baeldung.springai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

    @Bean
    public ChatClient chatClient(
            ChatClient.Builder builder,
            LlmJudgeAdvisor judgeAdvisor
    ) {
        return builder
                .defaultAdvisors(judgeAdvisor)
                .build();
    }

    @Bean
    public LlmJudgeAdvisor llmJudgeAdvisor(
            ChatClient.Builder builder,
            @Value("${judge.score-threshold:0.7}") double scoreThreshold,
            @Value("${judge.max-refinements:2}") int maxRefinements
    ) {
        return new LlmJudgeAdvisor(
                builder.build(),
                scoreThreshold,
                maxRefinements
        );
    }

}
