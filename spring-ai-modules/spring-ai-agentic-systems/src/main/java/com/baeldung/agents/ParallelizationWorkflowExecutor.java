package com.baeldung.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
class ParallelizationWorkflowExecutor {

    private final ChatClient chatClient;

    ParallelizationWorkflowExecutor(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    List<String> execute(PromptTemplate promptTemplate, List<String> inputs) {
        try (ExecutorService executor = Executors.newFixedThreadPool(inputs.size())) {
            List<CompletableFuture<String>> futures = inputs
                .stream()
                .map(input -> CompletableFuture.supplyAsync(() -> chatClient
                    .prompt(promptTemplate.render(Map.of("input", input)))
                    .call()
                    .content()
                , executor))
                .toList();

            return futures.stream()
                .map(CompletableFuture::join)
                .toList();
        }
    }

}