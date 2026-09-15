package com.baeldung.springai.anthropic;

import java.util.UUID;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

@Service
public class ChatWithPromptCachingService {

    private final ChatClient chatClient;

    public ChatWithPromptCachingService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public ChatResponseWithMetadataDto chat(String userMessage) {
        return chat(userMessage, PromptsUtils.LONG_SYSTEM_PROMPT);
    }

    public ChatResponseWithMetadataDto chat(String userMessage, UUID uniquePromptId) {
        return chat(userMessage, PromptsUtils.LONG_SYSTEM_PROMPT + "\nTEST_ID=" + uniquePromptId);
    }

    public ChatResponseWithMetadataDto chat(String userMessage, String systemPrompt) {
        ChatResponse response = chatClient
            .prompt()
            .system(systemPrompt)
            .user(userMessage)
            .call()
            .chatClientResponse()
            .chatResponse();

        if (response == null || response.getResult() == null) {
            throw new RuntimeException("Client response has no results");
        }

        return ChatResponseWithMetadataDto.fromChatResponse(response);
    }
}
