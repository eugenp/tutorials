package com.baeldung.springai.chat;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class ChatService {

    private final GoogleGenAiChatModel chatModel;
    private final ChatClient defaultChatClient;
    private final ChatClient fluentChatClient;

    public ChatService(GoogleGenAiChatModel chatModel, ChatClient.Builder chatClientBuilder) {
        this.chatModel = chatModel;
        this.defaultChatClient = chatClientBuilder.build();
        this.fluentChatClient = chatClientBuilder
            .defaultSystem("You are a concise technical writer summarizing software concepts.")
            .build();
    }

    public String simplifiedPrompt(String message) {
        return chatModel.call(message);
    }

    public String fluentPrompt(String prompt) {
        return this.fluentChatClient.prompt()
            .user(prompt)
            .call()
            .content();
    }

    public String reviewCode(String language, String codeSnippet) {
        String template = """
            Analyze the following {language} code snippet for memory leaks or inefficiencies.
            Provide an optimized version.

            Code:
            {code}
            """;

        return this.defaultChatClient.prompt()
            .user(u -> u.text(template)
                .params(Map.of("language", language, "code", codeSnippet)))
            .call()
            .content();
    }

    public String searchGroundedPrompt(String currentEventQuery) {
        return this.defaultChatClient.prompt()
            .user(currentEventQuery)
            .call()
            .content();
    }

    public Flux<String> streamChatTokens(String prompt) {
        return this.defaultChatClient.prompt()
            .user(prompt)
            .stream()
            .content();
    }
}
