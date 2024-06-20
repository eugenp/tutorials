package com.baeldung.spring.ai.ollamachatbot.service;

import java.util.Map;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.stereotype.Service;

import com.baeldung.spring.ai.ollamachatbot.model.ChatBotRequest;
import com.baeldung.spring.ai.ollamachatbot.model.ChatBotResponse;

@Service
public class ChatBotService {
    private final OllamaChatClient ollamaChatClient;

    public ChatBotService(OllamaChatClient chatClient) {
        this.ollamaChatClient = chatClient;
    }

    public ChatBotResponse chat(ChatBotRequest chatBotRequest) {
        Prompt chatPrompt = new Prompt(chatBotRequest.getPromptMessage());
        ChatResponse chatResponse = ollamaChatClient.call(chatPrompt);

        return new ChatBotResponse();
    }
}
