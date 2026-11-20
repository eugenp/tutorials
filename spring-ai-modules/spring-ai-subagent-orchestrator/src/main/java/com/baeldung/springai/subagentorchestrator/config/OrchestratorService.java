package com.baeldung.springai.subagentorchestrator.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class OrchestratorService {

    private final ChatClient chatClient;

    public OrchestratorService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String ask(String userMessage) {
        return chatClient
            .prompt(userMessage)
            .call()
            .content();
    }
}
