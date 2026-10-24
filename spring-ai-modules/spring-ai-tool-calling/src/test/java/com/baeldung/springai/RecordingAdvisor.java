package com.baeldung.springai;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;

class RecordingAdvisor implements CallAdvisor {

    private final String name;
    private final int order;
    private final List<ChatClientResponse> responses = new ArrayList<>();

    RecordingAdvisor(String name, int order) {
        this.name = name;
        this.order = order;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
        ChatClientResponse response = chain.nextCall(request);
        responses.add(response);
        return response;
    }

    List<ChatClientResponse> getResponses() {
        return responses;
    }
}
