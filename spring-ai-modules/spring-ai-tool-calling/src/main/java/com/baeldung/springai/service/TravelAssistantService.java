package com.baeldung.springai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.baeldung.springai.tools.BookingTools;

@Service
public class TravelAssistantService {

    private final ChatClient chatClient;
    private final ChatClient memoryChatClient;
    private final BookingTools bookingTools;
    private final ToolCallback currentWeather;

    public TravelAssistantService(ChatClient chatClient,
        @Qualifier("memoryTravelChatClient") ChatClient memoryChatClient,
        BookingTools bookingTools,
        @Qualifier("currentWeather") ToolCallback currentWeather) {
        this.chatClient = chatClient;
        this.memoryChatClient = memoryChatClient;
        this.bookingTools = bookingTools;
        this.currentWeather = currentWeather;
    }

    public String planTrip(String request) {
        return chatClient.prompt()
            .system("Help the user plan a trip. Never book a flight without an explicit request.")
            .user(request)
            .tools(bookingTools)
            .call()
            .content();
    }

    public String chatWithMemory(String conversationId, String message) {
        return memoryChatClient.prompt()
            .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, conversationId))
            .user(message)
            .call()
            .content();
    }

    public String planTripInSession(String sessionId, String request) {
        return chatClient.prompt()
            .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, sessionId))
            .user(request)
            .call()
            .content();
    }

    public String askWithFunctionCallback(String question) {
        return chatClient.prompt()
            .user(question)
            .tools(currentWeather)
            .call()
            .content();
    }
}
