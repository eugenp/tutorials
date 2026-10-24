package com.baeldung.springai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.AssistantMessage.ToolCall;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;

/**
 * Deterministic {@link ChatModel} that replays a predefined sequence of responses and records every prompt it receives.
 */
public class ScriptedChatModel implements ChatModel {

    private final Deque<ChatResponse> responses;
    private final List<Prompt> prompts = new ArrayList<>();

    public ScriptedChatModel(ChatResponse... responses) {
        this.responses = new ConcurrentLinkedDeque<>(Arrays.asList(responses));
    }

    public static ChatResponse toolCall(String toolName, String arguments) {
        ToolCall toolCall = new ToolCall(UUID.randomUUID().toString(), "function", toolName, arguments);
        AssistantMessage message = AssistantMessage.builder()
            .content("")
            .toolCalls(List.of(toolCall))
            .build();
        return new ChatResponse(List.of(new Generation(message)));
    }

    public static ChatResponse answer(String text) {
        return new ChatResponse(List.of(new Generation(new AssistantMessage(text))));
    }

    @Override
    public ChatResponse call(Prompt prompt) {
        prompts.add(prompt);
        ChatResponse response = responses.poll();
        if (response == null) {
            throw new IllegalStateException("No more scripted responses");
        }
        return response;
    }

    @Override
    public ChatOptions getOptions() {
        return ToolCallingChatOptions.builder().build();
    }

    public List<Prompt> getPrompts() {
        return prompts;
    }
}
