package com.baeldung.springai.evaluator;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.evaluation.FactCheckingEvaluator;
import org.springframework.ai.evaluation.RelevancyEvaluator;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.ollama.management.ModelManagementOptions;
import org.springframework.ai.ollama.management.PullModelStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LLMConfiguration {

    @Bean
    public ChatClient contentGenerator(ChatModel chatModel) {
        return ChatClient.builder(chatModel).build();
    }

    @Bean
    public ChatClient contentEvaluator(OllamaApi olamaApi) {
        ChatModel chatModel = OllamaChatModel.builder()
            .ollamaApi(olamaApi)
            .defaultOptions(OllamaOptions.builder()
                .model("bespoke-minicheck")
                .build())
            .modelManagementOptions(ModelManagementOptions.builder()
                .pullModelStrategy(PullModelStrategy.WHEN_MISSING)
                .build())
            .build();
        return ChatClient.builder(chatModel).build();
    }

    @Bean
    public FactCheckingEvaluator factCheckingEvaluator(@Qualifier("contentEvaluator") ChatClient chatClient) {
        return new FactCheckingEvaluator(chatClient.mutate());
    }

    @Bean
    public RelevancyEvaluator relevancyEvaluator(@Qualifier("contentEvaluator") ChatClient chatClient) {
        return new RelevancyEvaluator(chatClient.mutate());
    }

}