package com.baeldung.springai.deepseek;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

}