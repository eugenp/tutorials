package com.baeldung.springai;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;

public class QualityCheckAdvisor implements CallAdvisor {
    private static final int MAX_RETRIES = 3;
    private static final String ADVISOR_NAME = "QualityCheckAdvisor";

    @Override
    public String getName() {
        return ADVISOR_NAME;
    }

    @Override
    public int getOrder() {
        return BaseAdvisor.LOWEST_PRECEDENCE - 100;
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
        ChatClientResponse response = chain.nextCall(request);
        int attempts = 0;
        while (attempts < MAX_RETRIES && !isHighQuality(response)) {
            String feedback = "Your previous answer was incomplete. " + "Please provide a more thorough response.";
            var augmentedPrompt = request
                    .prompt()
                    .augmentUserMessage(userMessage -> userMessage.mutate().text(userMessage.getText() + System.lineSeparator() + feedback).build());
            var augmentedRequest = request
                    .mutate()
                    .prompt(augmentedPrompt)
                    .build();
            response = chain
                    .copy(this)
                    .nextCall(augmentedRequest);
            attempts++;
        }
        return response;
    }

    private boolean isHighQuality(ChatClientResponse response) {
        String content = response.chatResponse().getResult().getOutput().getText();
        return content != null && content.length() > 200;
    }
}