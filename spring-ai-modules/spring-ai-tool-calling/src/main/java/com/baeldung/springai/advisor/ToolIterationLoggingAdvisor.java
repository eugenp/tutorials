package com.baeldung.springai.advisor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.ToolCallingAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;

public class ToolIterationLoggingAdvisor implements CallAdvisor {

    private static final Logger log = LoggerFactory.getLogger(ToolIterationLoggingAdvisor.class);

    @Override
    public String getName() {
        return ToolIterationLoggingAdvisor.class.getSimpleName();
    }

    @Override
    public int getOrder() {
        return ToolCallingAdvisor.DEFAULT_ORDER + 100;
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
        ChatClientResponse response = chain.nextCall(request);
        log.info("Tool-loop response: {}", response.chatResponse());
        return response;
    }
}
