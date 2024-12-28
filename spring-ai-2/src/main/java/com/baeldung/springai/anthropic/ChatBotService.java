package com.baeldung.springai.anthropic;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.model.Media;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ChatBotService {

    private final ChatClient chatClient;

    public ChatBotService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public ChatResponse chat(ChatRequest chatRequest) {
        UUID chatId = Optional
            .ofNullable(chatRequest.chatId())
            .orElse(UUID.randomUUID());
        String answer = chatClient
            .prompt()
            .user(chatRequest.question())
            .advisors(advisorSpec ->
                advisorSpec
                    .param("chat_memory_conversation_id", chatId))
            .call()
            .content();
        return new ChatResponse(chatId, answer);
    }

    public ChatResponse chat(ChatRequest chatRequest, MultipartFile... files) {
        UUID chatId = Optional
            .ofNullable(chatRequest.chatId())
            .orElse(UUID.randomUUID());
        String answer = chatClient
            .prompt()
            .user(promptUserSpec ->
                promptUserSpec
                    .text(chatRequest.question())
                    .media(convert(files)))
            .advisors(advisorSpec ->
                advisorSpec
                    .param("chat_memory_conversation_id", chatId))
            .call()
            .content();
        return new ChatResponse(chatId, answer);
    }

    private Media[] convert(MultipartFile... files) {
        return Stream.of(files)
            .map(file -> new Media(
                MimeType.valueOf(file.getContentType()),
                file.getResource()
            ))
            .toArray(Media[]::new);
    }

}