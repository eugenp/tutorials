package com.baeldung.springai.anthropic;

import org.springframework.ai.chat.model.ChatResponse;

public record ChatResponseWithMetadataDto(String responseText,
                                          Integer promptTokens,
                                          Integer completionTokens,
                                          Long cacheReadInputTokens,
                                          Long cacheWriteInputTokens) {

    public static ChatResponseWithMetadataDto fromChatResponse(ChatResponse chatResponse) {
        if (chatResponse == null || chatResponse.getResult() == null) {
            throw new RuntimeException("Client response has no results");
        }

        String responseText = chatResponse.getResult()
            .getOutput()
            .getText();
        Integer promptTokens = chatResponse.getMetadata()
            .getUsage()
            .getPromptTokens();
        Integer completionTokens = chatResponse.getMetadata()
            .getUsage()
            .getCompletionTokens();
        Long cacheReadInputTokens = chatResponse.getMetadata()
            .getUsage()
            .getCacheReadInputTokens();
        Long cacheWriteInputTokens = chatResponse.getMetadata()
            .getUsage()
            .getCacheWriteInputTokens();

        return new ChatResponseWithMetadataDto(responseText, promptTokens, completionTokens, cacheReadInputTokens, cacheWriteInputTokens);
    }
}
