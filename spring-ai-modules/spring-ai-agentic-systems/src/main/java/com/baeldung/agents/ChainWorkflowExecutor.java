package com.baeldung.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Component
class ChainWorkflowExecutor {

    private final ChatClient chatClient;
    private final List<PromptTemplate> systemPrompts;

    ChainWorkflowExecutor(ChatClient.Builder chatClientBuilder, ResourcePatternResolver resourcePatternResolver) throws IOException {
        this.chatClient = chatClientBuilder.build();
        this.systemPrompts = Arrays
            .stream(resourcePatternResolver.getResources("classpath:chain-workflow/*.st"))
            .sorted(Comparator.comparing(Resource::getFilename))
            .map(PromptTemplate::new)
            .toList();
    }

    String execute(String userInput) {
        String currentInput = userInput;
        for (PromptTemplate systemPrompt: systemPrompts) {
            currentInput = chatClient
                .prompt(systemPrompt.render(Map.of("input", currentInput)))
                .call()
                .content();
        }
        return currentInput;
    }

}