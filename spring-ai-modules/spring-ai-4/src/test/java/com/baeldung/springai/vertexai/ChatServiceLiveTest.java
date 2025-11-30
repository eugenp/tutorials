package com.baeldung.springai.vertexai;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("vertexai")
class ChatServiceLiveTest {

    private static final String PROMPT = "Tell me who you are?";

    @Autowired
    private ChatService chatService;

    @Test
    void whenChatServiceIsCalled_thenServiceReturnsNonEmptyResponse() {
        String response = chatService.chat(PROMPT);
        assertThat(response).isNotEmpty();
    }

}