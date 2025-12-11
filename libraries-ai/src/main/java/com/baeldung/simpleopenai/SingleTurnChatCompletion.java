package com.baeldung.simpleopenai;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.concurrent.CompletableFuture;

import io.github.sashirestela.openai.domain.chat.Chat;
import io.github.sashirestela.openai.domain.chat.ChatMessage.UserMessage;
import io.github.sashirestela.openai.domain.chat.ChatRequest;

public class SingleTurnChatCompletion {

    public static void main(String[] args) {
        var client = Client.getClient();

        ChatRequest chatRequest = ChatRequest.builder()
            .model(Client.CHAT_MODEL)
            .message(UserMessage.of(
                "Suggest a weekend trip in Japan, no more than 60 words."
            ))
            .build();

        CompletableFuture<Chat> chatFuture =
            client.chatCompletions().create(chatRequest);
        Chat chat = chatFuture.join();

        Logger logger = Client.LOGGER;
        logger.log(Level.INFO, "Model reply: {0}", chat.firstContent());
    }
}
