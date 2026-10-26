package com.baeldung.springai.service;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.ai.chat.client.AdvisorParams;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.messages.AssistantMessage.ToolCall;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.model.tool.ToolExecutionResult;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.baeldung.springai.tools.BookingTools;
import com.baeldung.springai.tools.WeatherTools;

@Service
public class ManualToolExecutionService {

    private final ChatClient chatClient;
    private final WeatherTools weatherTools;
    private final BookingTools bookingTools;

    public ManualToolExecutionService(@Qualifier("manualToolExecutionChatClient") ChatClient chatClient,
        WeatherTools weatherTools, BookingTools bookingTools) {
        this.chatClient = chatClient;
        this.weatherTools = weatherTools;
        this.bookingTools = bookingTools;
    }

    public String planTrip(String question, Predicate<ToolCall> bookingApprover) {
        ToolCallingManager toolCallingManager = ToolCallingManager.builder().build();
        ToolCallback[] callbacks = ToolCallbacks.from(weatherTools, bookingTools);
        ToolCallingChatOptions options = ToolCallingChatOptions.builder()
            .toolCallbacks(callbacks)
            .build();

        Prompt prompt = new Prompt(List.of(new UserMessage(question)), options);

        ChatClientResponse response = chatClient.prompt()
            .messages(prompt.getInstructions())
            .options(options.mutate())
            .advisors(AdvisorParams.toolCallingAdvisorAutoRegister(false))
            .call()
            .chatClientResponse();

        while (response.chatResponse() != null && response.chatResponse().hasToolCalls()) {
            approveIfRequired(response.chatResponse(), bookingApprover);

            ToolExecutionResult result = toolCallingManager.executeToolCalls(prompt, response.chatResponse());
            prompt = new Prompt(result.conversationHistory(), options);

            response = chatClient.prompt()
                .messages(result.conversationHistory())
                .options(options.mutate())
                .advisors(AdvisorParams.toolCallingAdvisorAutoRegister(false))
                .call()
                .chatClientResponse();
        }

        return response.chatResponse() != null ? response.chatResponse().getResult().getOutput().getText() : null;
    }

    private void approveIfRequired(ChatResponse chatResponse, Predicate<ToolCall> bookingApprover) {
        chatResponse.getResults().stream()
            .flatMap(generation -> generation.getOutput().getToolCalls().stream())
            .filter(toolCall -> "bookFlight".equals(toolCall.name()))
            .filter(bookingApprover.negate())
            .findFirst()
            .ifPresent(toolCall -> {
                throw new BookingNotApprovedException("Booking was not approved: " + toolCall.arguments());
            });
    }
}
