package com.baeldung.springai.lmstudio;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String chat(String prompt) {
        return chatClient.prompt()
            .user(userMessage -> userMessage.text(prompt))
            .call()
            .content();
    }
}