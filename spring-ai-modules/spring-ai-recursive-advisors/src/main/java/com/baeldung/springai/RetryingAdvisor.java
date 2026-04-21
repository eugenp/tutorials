package com.baeldung.springai;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;

public class RetryingAdvisor implements CallAdvisor {
    private static final int MAX_ATTEMPTS = 5;

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
        ChatClientResponse response = chain.nextCall(request);
        int attempt = 0;
        while (attempt < MAX_ATTEMPTS && needsRetry(response)) {
            ChatClientRequest updatedRequest = augmentWithFeedback(request, response);
            response = chain.copy(this).nextCall(updatedRequest);
            attempt++;
        }
        return response;
    }

    private boolean needsRetry(ChatClientResponse response) {
        return response.chatResponse().getResult().getOutput().getText().contains("I'm sorry");
    }

    private ChatClientRequest augmentWithFeedback(ChatClientRequest request, ChatClientResponse response) {
        var augmentedPrompt = request
                .prompt()
                .augmentUserMessage(userMessage -> userMessage.mutate().text(userMessage.getText() + System.lineSeparator() + "Please provide a more thorough response.").build());
        return request
                .mutate()
                .prompt(augmentedPrompt)
                .build();
    }

    @Override
    public String getName() {
        return "RetryingAdvisor";
    }

    @Override
    public int getOrder() {
        return BaseAdvisor.LOWEST_PRECEDENCE - 100;
    }
}