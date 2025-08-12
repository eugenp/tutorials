package com.baeldung.springai.nova;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

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
            .hasNoNullFieldsOrProperties();
        assertThat(chatResponse.answer())
            .contains("Martha");

        ChatRequest followUpChatRequest = new ChatRequest(chatResponse.chatId(), "Which bald billionaire hates him?");
        ChatResponse followUpChatResponse = chatbotService.chat(followUpChatRequest);

        assertThat(followUpChatResponse)
            .isNotNull()
            .hasNoNullFieldsOrProperties();
        assertThat(followUpChatResponse.answer())
            .contains("Lex Luthor");
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
            .hasNoNullFieldsOrProperties();
        assertThat(chatResponse.answer())
            .containsAnyOf("Batman", "Deadpool", "Santa Claus", "Christmas");
    }

    @Test
    void whenChatbotCalledWithAuthorQuery_thenDetailsFetchedUsingFunctionCalling() {
        ChatRequest chatRequest = new ChatRequest(null, "Who wrote the article 'Testing CORS in Spring Boot' and how can I contact him?");
        ChatResponse chatResponse = chatbotService.chat(chatRequest);

        assertThat(chatResponse)
            .isNotNull()
            .hasNoNullFieldsOrProperties();
        assertThat(chatResponse.answer())
            .contains("John Doe", "john.doe@baeldung.com");
    }

}