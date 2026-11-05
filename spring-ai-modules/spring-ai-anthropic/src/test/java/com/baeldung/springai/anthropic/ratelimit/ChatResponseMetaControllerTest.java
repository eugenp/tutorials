package com.baeldung.springai.anthropic.ratelimit;

import java.time.Duration;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class ChatResponseMetaControllerTest {

    private static final String DEFAULT_MESSAGE = "Tell a story in less than 100 words";

    private MockMvc mockMvc;
    private ChatClient chatClient;

    @BeforeEach
    void setUp() {
        ChatClient.Builder builder = mock(ChatClient.Builder.class);
        chatClient = mock(ChatClient.class, Mockito.RETURNS_DEEP_STUBS);
        when(builder.build()).thenReturn(chatClient);

        ChatResponse chatResponse = mock(ChatResponse.class, Mockito.RETURNS_DEEP_STUBS);
        when(chatResponse.getMetadata().getModel()).thenReturn("claude-sonnet");
        when(chatResponse.getMetadata().getRateLimit().getRequestsLimit()).thenReturn(100L);
        when(chatResponse.getMetadata().getRateLimit().getRequestsRemaining()).thenReturn(90L);
        when(chatResponse.getMetadata().getRateLimit().getTokensReset()).thenReturn(Duration.ofSeconds(30));
        when(chatResponse.getMetadata().getRateLimit().getTokensLimit()).thenReturn(10000L);
        when(chatResponse.getMetadata().getRateLimit().getTokensRemaining()).thenReturn(9000L);
        when(chatResponse.getMetadata().getUsage().getPromptTokens()).thenReturn(12);
        when(chatResponse.getMetadata().getUsage().getCompletionTokens()).thenReturn(24);
        when(chatResponse.getResult().getOutput().getText()).thenReturn("A short story.");

        when(chatClient.prompt().user(DEFAULT_MESSAGE).call().chatResponse()).thenReturn(chatResponse);
        when(chatClient.prompt().user("Write a story").call().chatResponse()).thenReturn(chatResponse);

        mockMvc = MockMvcBuilders.standaloneSetup(new ChatResponseMetaController(builder))
            .build();
    }

    @Test
    void givenNoMessage_whenStoryEndpointCalled_thenUsesDefaultAndReturnsMetadata() throws Exception {
        mockMvc.perform(get("/story-gen"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.model").value("claude-sonnet"))
            .andExpect(jsonPath("$.requestsLimit").value(100))
            .andExpect(jsonPath("$.requestsRemaining").value(90))
            .andExpect(jsonPath("$.tokensReset").exists())
            .andExpect(jsonPath("$.tokensLimit").value(10000))
            .andExpect(jsonPath("$.tokensRemaining").value(9000))
            .andExpect(jsonPath("$.promptTokens").value(12))
            .andExpect(jsonPath("$.completionTokens").value(24))
            .andExpect(jsonPath("$.content").value("A short story."));
    }

    @Test
    void givenMessage_whenStoryEndpointCalled_thenPassesMessageToChatClient() throws Exception {
        mockMvc.perform(get("/story-gen").param("message", "Write a story"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").value("A short story."));
    }
}
