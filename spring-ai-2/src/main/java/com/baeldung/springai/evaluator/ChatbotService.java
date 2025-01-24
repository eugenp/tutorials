package com.baeldung.springai.evaluator;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ChatbotService {

    private final ChatClient chatClient;

    public ChatbotService(@Qualifier("contentGenerator") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public ChatResponse chat(Prompt prompt) {
        return chatClient
            .prompt(prompt)
            .call()
            .chatResponse();
    }

}