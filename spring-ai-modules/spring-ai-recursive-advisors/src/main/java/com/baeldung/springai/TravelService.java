package com.baeldung.springai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.stereotype.Service;

@Service
public class TravelService {

    private final ChatClient chatClient;

    // ChatClient.Builder is injectable
    public TravelService(ChatClient.Builder builder) {
        this.chatClient = builder
                // we add a default advisor that logs input/output
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    public String getTravelTip(String destination) {
        return this.chatClient
                .prompt()
                .user("Give me three insider tips for a trip to: " + destination)
                .call()
                .content();
    }
}