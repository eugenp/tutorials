package com.baeldung.springai.deepseek;

import java.util.Optional;
import java.util.UUID;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
class ChatbotService {

    private final ChatClient chatClient;

    ChatbotService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    ChatResponse chat(ChatRequest chatRequest) {
        UUID chatId = Optional
            .ofNullable(chatRequest.chatId())
            .orElse(UUID.randomUUID());
        DeepSeekModelResponse response = chatClient
            .prompt()
            .user(chatRequest.question())
            .advisors(advisorSpec ->
                advisorSpec
                    .param("chat_memory_conversation_id", chatId))
            .call()
            .entity(new DeepSeekModelOutputConverter());
        return new ChatResponse(chatId, response.chainOfThought(), response.answer());
    }

}