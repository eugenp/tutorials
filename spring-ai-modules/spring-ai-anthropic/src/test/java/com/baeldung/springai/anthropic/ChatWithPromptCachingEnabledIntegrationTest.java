package com.baeldung.springai.anthropic;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatWithPromptCachingEnabledIntegrationTest {

    @Autowired
    private ChatWithPromptCachingService service;
    private static final UUID TEST_ID = UUID.randomUUID();

    @Test
    void chat_whenPromptCachingEnabled_returnsResponse() {
        String chatMessage = "hello there";

        ChatResponseWithMetadataDto response = service.chat(chatMessage, TEST_ID);

        System.out.println("Response1: " + response);
        System.out.println("Response1 promptTokens: " + response.promptTokens());
        System.out.println("Response1 completionTokens: " + response.completionTokens());
        assertThat(response).isNotNull();
        assertThat(response.promptTokens()).isLessThan(50);
        assertThat(response.cacheWriteInputTokens()).isGreaterThan(1000);
        assertThat(response.cacheReadInputTokens()).isEqualTo(0);


        response = service.chat(chatMessage + " again", TEST_ID);

        System.out.println("Response2: " + response);
        System.out.println("Response2 promptTokens: " + response.promptTokens());
        System.out.println("Response2 completionTokens: " + response.completionTokens());
        assertThat(response).isNotNull();
        assertThat(response.promptTokens()).isLessThan(50);
        assertThat(response.cacheWriteInputTokens()).isEqualTo(0);
        assertThat(response.cacheReadInputTokens()).isGreaterThan(1000);
    }
}