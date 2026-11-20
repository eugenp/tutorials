package com.baeldung.springai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.stereotype.Service;

@Service
public class TravelService {

    private final ChatClient chatClient;

    public TravelService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String getTravelTip(String destination) {
        return this.chatClient
                .prompt()
                .user("Give me three insider tips for a trip to: " + destination)
                .call()
                .content();
    }
}