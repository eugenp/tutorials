package com.baeldung.springai.openai.sdk;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class SupportSummaryService {

    private final ChatClient chatClient;

    public SupportSummaryService(ChatClient.Builder builder) {
        this.chatClient = builder.defaultSystem(
                "Summarize the support request in one sentence. Include the problem and the requested action. Do not invent details.")
                .build();
    }

    public String summarize(String request) {
        return chatClient.prompt().user(request).call().content();
    }
}
