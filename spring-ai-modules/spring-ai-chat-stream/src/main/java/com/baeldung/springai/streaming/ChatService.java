package com.baeldung.springai.streaming;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

@Component
public class ChatService {

    private final ChatClient chatClient;

    public ChatService(ChatModel chatModel) {
        this.chatClient = ChatClient.builder(chatModel)
                .build();
    }

    public Flux<String> chat(String prompt) {
        return chatClient.prompt()
            .user(userMessage -> userMessage.text(prompt))
            .stream()
            .content();
    }

    public Flux<String> chatAsWord(String prompt) {
        return chatClient.prompt()
            .user(userMessage -> userMessage.text(prompt))
            .stream()
            .content();
    }

    public Flux<String> chatAsChunk(String prompt) {
        return chatClient.prompt()
            .user(userMessage -> userMessage.text(prompt))
            .stream()
            .content()
            .transform(flux -> toChunk(flux, 100));
    }

    public Flux<String> chatAsJson(String prompt) {
        return chatClient.prompt()
            .system(systemMessage -> systemMessage.text(
                """
                  Respond in NDJSON format.
                  Each JSON object should contains around 100 characters.
                  Sample json object format: {"part":0,"text":"Once in a small town..."}
                """))
                .user(userMessage -> userMessage.text(prompt))
                .stream()
                .content()
                .transform(this::toJsonChunk);
    }

    private Flux<String> toChunk(Flux<String> tokenFlux, int chunkSize) {
        return Flux.create(sink -> {
            StringBuilder buffer = new StringBuilder();
            tokenFlux.subscribe(
                token -> {
                    buffer.append(token);
                    if (buffer.length() >= chunkSize) {
                        sink.next(buffer.toString());
                        buffer.setLength(0);
                    }
                },
                sink::error,
                () -> {
                    if (buffer.length() > 0) {
                        sink.next(buffer.toString());
                    }
                    sink.complete();
                }
            );
        });
    }

    private Flux<String> toJsonChunk(Flux<String> tokenFlux) {
        return Flux.create(sink -> {
            StringBuilder buffer = new StringBuilder();
            tokenFlux.subscribe(
                token -> {
                    buffer.append(token);
                    int idx;
                    if ((idx = buffer.indexOf("\n")) >= 0) {
                        String line = buffer.substring(0, idx);
                        sink.next(line);
                        buffer.delete(0, idx + 1);
                    }
                },
                sink::error,
                () -> {
                    if (buffer.length() > 0) {
                        sink.next(buffer.toString());
                    }
                    sink.complete();
                }
            );
        });
    }

}