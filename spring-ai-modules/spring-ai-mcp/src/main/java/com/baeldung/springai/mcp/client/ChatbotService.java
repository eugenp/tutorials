package com.baeldung.springai.mcp.client;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
class ChatbotService {

    private final ChatClient chatClient;

    ChatbotService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    String chat(String question) {
        return chatClient
            .prompt()
            .user(question)
            .call()
            .content();
    }

}