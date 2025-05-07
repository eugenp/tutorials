package com.baeldung.springai.groq;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class GroqChatService {

    private final ChatClient chatClient;

    public GroqChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String getChatResponse(String userInput) {

        Prompt prompt = new Prompt(new UserMessage(userInput));
        // Call the chat client and get the response
        return this.chatClient.prompt()
            .user(userInput)
            .call()
            .content();
    }
}
