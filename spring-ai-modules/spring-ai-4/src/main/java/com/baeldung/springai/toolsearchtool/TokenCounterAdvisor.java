package com.baeldung.springai.toolsearchtool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.AdvisorChain;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.core.Ordered;

import java.util.concurrent.atomic.AtomicInteger;


public class TokenCounterAdvisor implements BaseAdvisor {
    private static final Logger log = LoggerFactory.getLogger(TokenCounterAdvisor.class);

    private final AtomicInteger totalTokenCounter = new AtomicInteger(0);

    @Override
    public String getName() {
        return "TokenCounterAdvisor";
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 1;
    }

    @Override
    public ChatClientRequest before(ChatClientRequest chatClientRequest, AdvisorChain advisorChain) {
        return chatClientRequest;
    }

    @Override
    public ChatClientResponse after(ChatClientResponse chatClientResponse, AdvisorChain advisorChain) {
        var usage = chatClientResponse.chatResponse().getMetadata().getUsage();

        totalTokenCounter.addAndGet(usage.getTotalTokens());

        System.out.println("Current TOKENS Total: " + usage.getTotalTokens() + ", Completion: "
                + usage.getCompletionTokens() + ", Prompt: " + usage.getPromptTokens());

        log.info("Total tokens spent: {}", totalTokenCounter.get());

        return chatClientResponse;
    }
}
