package com.baeldung.simpleopenai;

import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

import io.github.sashirestela.openai.domain.chat.Chat;
import io.github.sashirestela.openai.domain.chat.ChatMessage;
import io.github.sashirestela.openai.domain.chat.ChatMessage.AssistantMessage;
import io.github.sashirestela.openai.domain.chat.ChatMessage.SystemMessage;
import io.github.sashirestela.openai.domain.chat.ChatMessage.UserMessage;
import io.github.sashirestela.openai.domain.chat.ChatRequest;

public class KeepingConversationStateInJava {

    public static void main(String[] args) {
        var client = Client.getClient();

        List<ChatMessage> history = new ArrayList<>();
        history.add(SystemMessage.of(
            "You are a helpful travel assistant. Answer briefly."
            ));

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("You: ");
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

                CompletableFuture<Chat> chatFuture =
                    client.chatCompletions().create(chatRequest);
                Chat chat = chatFuture.join();

                String reply = chat.firstContent().toString();
                Client.LOGGER.log(Level.INFO, "Assistant: {0}", reply);

                history.add(AssistantMessage.of(reply));
            }
        }
    }
}
