package com.baeldung.springai.outputguardrail;

import java.util.Arrays;
import java.util.List;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;

public class OutputSafeGuardAdvisor implements CallAdvisor {

    private final List<String> sensitiveWords;
    private final String failureResponse;
    private final int order;

    private OutputSafeGuardAdvisor(
      List<String> sensitiveWords, String failureResponse, int order) {
        this.sensitiveWords = sensitiveWords;
        this.failureResponse = failureResponse;
        this.order = order;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public ChatClientResponse adviseCall(
      ChatClientRequest request, CallAdvisorChain chain) {
        ChatClientResponse response = chain.nextCall(request);
        ChatResponse chatResponse = response.chatResponse();
        if (chatResponse == null || chatResponse.getResults().isEmpty()) {
            return response;
        }

        List<Generation> results = chatResponse.getResults();
        List<Generation> filteredResults = results.stream()
          .map(this::filterGeneration)
          .toList();
        if (filteredResults.equals(results)) {
            return response;
        }

        ChatResponse filtered = new ChatResponse(
          filteredResults, chatResponse.getMetadata());

        return response.mutate()
          .chatResponse(filtered)
          .build();
    }

    private Generation filterGeneration(Generation generation) {
        if (!containsSensitiveWord(generation.getOutput().getText())) {
            return generation;
        }

        return new Generation(
          new AssistantMessage(failureResponse), generation.getMetadata());
    }

    private boolean containsSensitiveWord(String text) {
        if (!StringUtils.hasText(text)) {
            return false;
        }

        return sensitiveWords.stream()
          .anyMatch(text::contains);
    }

    public static class Builder {

        private List<String> sensitiveWords = List.of();
        private String failureResponse
          = "Response contained forbidden content.";
        private int order = Ordered.LOWEST_PRECEDENCE - 1;

        public Builder sensitiveWords(String... sensitiveWords) {
            this.sensitiveWords = Arrays.asList(sensitiveWords);
            return this;
        }

        public Builder sensitiveWords(List<String> sensitiveWords) {
            this.sensitiveWords = sensitiveWords;
            return this;
        }

        public Builder failureResponse(String failureResponse) {
            this.failureResponse = failureResponse;
            return this;
        }

        public Builder order(int order) {
            this.order = order;
            return this;
        }

        public OutputSafeGuardAdvisor build() {
            return new OutputSafeGuardAdvisor(
              sensitiveWords, failureResponse, order);
        }
    }
}
