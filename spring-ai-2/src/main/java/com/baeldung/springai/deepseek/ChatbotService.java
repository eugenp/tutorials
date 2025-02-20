package com.baeldung.springai.deepseek;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
class ChatbotService {

    private final ChatClient chatClient;
    private final DeepSeekModelOutputConverter deepSeekModelOutputConverter;

    ChatbotService(ChatClient chatClient, DeepSeekModelOutputConverter deepSeekModelOutputConverter) {
        this.chatClient = chatClient;
        this.deepSeekModelOutputConverter = deepSeekModelOutputConverter;
    }

    ChatResponse chat(ChatRequest chatRequest) {
        UUID chatId = Optional
            .ofNullable(chatRequest.chatId())
            .orElse(UUID.randomUUID());
        String llmResponse = chatClient
            .prompt()
            .user(chatRequest.question())
            .advisors(advisorSpec ->
                advisorSpec
                    .param("chat_memory_conversation_id", chatId))
            .call()
            .content();
        DeepSeekModelResponse deepSeekModelResponse = deepSeekModelOutputConverter.convert(llmResponse);
        return new ChatResponse(chatId, deepSeekModelResponse.chainOfThought(), deepSeekModelResponse.answer());
    }

}