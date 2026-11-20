package com.baeldung.springai.anthropic;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = { "spring.ai.anthropic.chat.cache-options.strategy=NONE" })
class ChatWithPromptCachingDisabledIntegrationTest {

    @Autowired
    private ChatWithPromptCachingService service;

    @Test
    void chat_whenPromptCachingDisabled_returnsResponse() {
        String chatMessage = "hello there";

        ChatResponseWithMetadataDto response = service.chat(chatMessage);

        System.out.println("Response1: " + response);
        System.out.println("Response1 promptTokens: " + response.promptTokens());
        System.out.println("Response1 completionTokens: " + response.completionTokens());
        assertThat(response).isNotNull();
        assertThat(response.promptTokens()).isGreaterThan(1030);
        assertThat(response.cacheReadInputTokens()).isEqualTo(0);

        response = service.chat(chatMessage + " again");

        System.out.println("Response2: " + response);
        System.out.println("Response2 promptTokens: " + response.promptTokens());
        System.out.println("Response2 completionTokens: " + response.completionTokens());
        assertThat(response).isNotNull();
        assertThat(response.promptTokens()).isGreaterThan(1030);
        assertThat(response.cacheReadInputTokens()).isEqualTo(0);
    }
}
