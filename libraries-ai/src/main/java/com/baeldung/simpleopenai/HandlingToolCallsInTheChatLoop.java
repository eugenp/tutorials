package com.baeldung.simpleopenai;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

import io.github.sashirestela.openai.common.function.FunctionExecutor;
import io.github.sashirestela.openai.common.tool.ToolCall;
import io.github.sashirestela.openai.domain.chat.Chat;
import io.github.sashirestela.openai.domain.chat.ChatMessage;
import io.github.sashirestela.openai.domain.chat.ChatMessage.AssistantMessage;
import io.github.sashirestela.openai.domain.chat.ChatMessage.SystemMessage;
import io.github.sashirestela.openai.domain.chat.ChatMessage.ToolMessage;
import io.github.sashirestela.openai.domain.chat.ChatMessage.UserMessage;
import io.github.sashirestela.openai.domain.chat.ChatRequest;
import io.github.sashirestela.openai.service.ChatCompletionServices;

public class HandlingToolCallsInTheChatLoop {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) {
        var client = Client.getClient();

        HotelService hotelService = new HotelService();
        FunctionExecutor functionExecutor = HotelFunctions.createFunctionExecutor(hotelService);

        List<ChatMessage> history = new ArrayList<>();
        history.add(SystemMessage.of(
            "You are a hotel booking assistant for a travel agency. " +
            "Use tools to search hotels and create bookings. " +
            "Do not invent hotel names, prices, or availability. " +
            "If required information is missing, ask a follow-up question."
        ));

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("\nYou: ");
                String input = scanner.nextLine();
                if (input == null || input.isBlank()) {
                    continue;
                }
                if ("exit".equalsIgnoreCase(input.trim())) {
                    break;
                }

                history.add(UserMessage.of(input));

                ChatMessage.ResponseMessage assistant =
                    runToolLoop(client, functionExecutor, history);

                String content = assistant.getContent();
                Client.LOGGER.log(Level.INFO, "Assistant: {0}", content);

                history.add(AssistantMessage.of(content));
            }
        }
    }

    private static ChatMessage.ResponseMessage runToolLoop(
        ChatCompletionServices client,
        FunctionExecutor functionExecutor,
        List<ChatMessage> history
    ) {
        while (true) {
            ChatRequest chatRequest = ChatRequest.builder()
                .model(Client.CHAT_MODEL)
                .messages(history)
                .tools(functionExecutor.getToolFunctions())
                .build();

            CompletableFuture<Chat> chatFuture =
                client.chatCompletions().create(chatRequest);

            Chat chat = chatFuture.join();
            ChatMessage.ResponseMessage message = chat.firstMessage();

            List<ToolCall> toolCalls = message.getToolCalls();
            if (toolCalls == null || toolCalls.isEmpty()) {
                return message;
            }

            List<ToolCall> sanitizedToolCalls = sanitizeToolCalls(toolCalls);

            history.add(AssistantMessage.builder()
                .content("")
                .toolCalls(sanitizedToolCalls)
                .build());

            for (ToolCall toolCall : sanitizedToolCalls) {
                String id = toolCall.getId();

                Client.LOGGER.log(Level.INFO,
                    "Tool call: {0} with args: {1} (id: {2})",
                    toolCall.getFunction().getName(),
                    toolCall.getFunction().getArguments(),
                    id
                );

                Object result = functionExecutor.execute(toolCall.getFunction());
                String payload = toJson(result);

                history.add(ToolMessage.of(payload, id));
            }
        }
    }

    private static List<ToolCall> sanitizeToolCalls(List<ToolCall> toolCalls) {
        List<ToolCall> sanitized = new ArrayList<>(toolCalls.size());
        int counter = 0;

        for (ToolCall toolCall : toolCalls) {
            counter++;

            String id = toolCall.getId();
            if (id == null || id.isBlank()) {
                id = toolCall.getFunction().getName() + "-" + counter;
            }

            sanitized.add(new ToolCall(
                toolCall.getIndex(),
                id,
                toolCall.getType(),
                toolCall.getFunction()
            ));
        }

        return sanitized;
    }

    private static String toJson(Object value) {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (Exception ex) {
            Client.LOGGER.log(Level.INFO,
                "Falling back to toString() for tool output: {0}",
                ex.getMessage()
            );
            return String.valueOf(value);
        }
    }
}
