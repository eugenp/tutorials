package com.baeldung.simpleopenai;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import io.github.sashirestela.openai.domain.chat.Chat;
import io.github.sashirestela.openai.domain.chat.ChatMessage;
import io.github.sashirestela.openai.domain.chat.ChatMessage.AssistantMessage;
import io.github.sashirestela.openai.domain.chat.ChatMessage.SystemMessage;
import io.github.sashirestela.openai.domain.chat.ChatMessage.UserMessage;
import io.github.sashirestela.openai.domain.chat.ChatRequest;

public class SwitchingToStreamingResponses {

    public static void main(String[] args) {
        var client = Client.getClient();

        List<ChatMessage> history = new ArrayList<>();
        history.add(SystemMessage.of(
            "You are a helpful travel assistant. Answer in at least 150 words."
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

                ChatRequest.ChatRequestBuilder chatRequestBuilder =
                    ChatRequest.builder().model(Client.CHAT_MODEL);

                for (ChatMessage message : history) {
                    chatRequestBuilder.message(message);
                }

                ChatRequest chatRequest = chatRequestBuilder.build();

                CompletableFuture<Stream<Chat>> chatStreamFuture =
                    client.chatCompletions().createStream(chatRequest);
                Stream<Chat> chatStream = chatStreamFuture.join();

                StringBuilder replyBuilder = new StringBuilder();

                chatStream.forEach(chunk -> {
                    String content = chunk.firstContent();
                    replyBuilder.append(content);
                    System.out.print(content);
                });

                String reply = replyBuilder.toString();
                history.add(AssistantMessage.of(reply));
            }
        }
    }
}
