package com.baeldung.springai.toolsearchtool;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("toolsearchtool")
class ToolsSearchToolLiveTest {
    @Autowired
    private ChatClient chatClient;

    @Autowired
    private ChatClient chatClientWithoutToolsSearch;

    @Test
    void shouldFindFlightsBetweenRomaniaAndCroatiaUsingToolsSearch() {
        String response = getClientResponseString(chatClient);
        assetClientResponse(response);
    }

    @Test
    void shouldFindFlightsBetweenRomaniaAndCroatiaWithoutToolsSearch() {
        String response = getClientResponseString(chatClientWithoutToolsSearch);
        assetClientResponse(response);
    }

    private static void assetClientResponse(String response) {
        assertThat(response).isNotBlank();
        assertThat(response).containsIgnoringCase("Croatia");
        assertThat(response).containsIgnoringCase("flight");
    }

    private String getClientResponseString(ChatClient chatClientWithoutToolsSearch) {
        return chatClientWithoutToolsSearch.prompt()
          .user("""
            Find available flights from Romania to Croatia next week.
            """)
          .call()
          .content();
    }
}