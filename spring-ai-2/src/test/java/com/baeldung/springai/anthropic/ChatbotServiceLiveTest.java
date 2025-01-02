package com.baeldung.springai.anthropic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ChatbotServiceLiveTest {

    @Autowired
    private ChatbotService chatbotService;

    @Test
    void whenChatbotCalledInSequence_thenConversationContextIsMaintained() {
        ChatRequest chatRequest = new ChatRequest(null, "What was the name of Superman's adoptive mother?");
        ChatResponse chatResponse = chatbotService.chat(chatRequest);

        assertThat(chatResponse)
            .isNotNull()
            .satisfies(response -> {
                assertThat(response.chatId())
                    .isNotNull();
                assertThat(response.answer())
                    .contains("Martha");
            });

        ChatRequest followUpChatRequest = new ChatRequest(chatResponse.chatId(), "Which hero had a breakdown when he heard it?");
        ChatResponse followUpChatResponse = chatbotService.chat(followUpChatRequest);

        assertThat(followUpChatResponse)
            .isNotNull()
            .satisfies(response -> {
                assertThat(response.answer())
                    .containsAnyOf("Batman", "Bruce Wayne");
            });
    }

    @Test
    void whenChatbotCalledWithImage_thenAccurateDescriptionGenerated() throws IOException {
        String imageName = "batman-deadpool-christmas.jpeg";
        ChatRequest chatRequest = new ChatRequest(null, "Describe the attached image.");
        MultipartFile image = new MockMultipartFile(
            imageName,
            null,
            "image/jpeg",
            new DefaultResourceLoader()
                .getResource("classpath:images/" + imageName)
                .getInputStream()
        );

        ChatResponse chatResponse = chatbotService.chat(chatRequest, image);
        assertThat(chatResponse)
            .isNotNull()
            .satisfies(response -> {
                assertThat(response.chatId())
                    .isNotNull();
                assertThat(response.answer())
                    .containsAnyOf("Batman", "Deadpool", "Santa Claus", "Christmas");
            });
    }

}