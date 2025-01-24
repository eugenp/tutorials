package com.baeldung.springai.evaluator;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;

@Component
public class ChatbotService {

    private final ChatClient contentGenerator;

    public ChatbotService(ChatClient contentGenerator) {
        this.contentGenerator = contentGenerator;
    }

    public ChatResponse chat(Prompt prompt) {
        return contentGenerator
            .prompt(prompt)
            .call()
            .chatResponse();
    }

}