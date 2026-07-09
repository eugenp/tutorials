package com.baeldung.springai.chat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = {
    "spring.ai.model.chat=google-genai",
    "spring.ai.google.genai.chat.model=gemini-2.5-flash",
    "spring.ai.google.genai.api-key=test-key"
})
@AutoConfigureMockMvc
class ChatControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatService chatService;

    @Test
    void whenSimpleEndpointIsInvoked_thenReturnsMockedModelResponse() throws Exception {
        when(chatService.simplifiedPrompt(anyString())).thenReturn("Mocked Gemini Response");

        mockMvc.perform(get("/v1/chat/simple").param("message", "Hello"))
            .andExpect(status().isOk())
            .andExpect(content().string("Mocked Gemini Response"));

        verify(chatService).simplifiedPrompt("Hello");
    }

    @Test
    void whenFluentEndpointIsInvoked_thenReturnsModelContent() throws Exception {
        when(chatService.fluentPrompt(anyString())).thenReturn("Fluent Response");

        mockMvc.perform(get("/v1/chat/fluent").param("prompt", "Explain DI"))
            .andExpect(status().isOk())
            .andExpect(content().string("Fluent Response"));

        verify(chatService).fluentPrompt("Explain DI");
    }

    @Test
    void whenReviewEndpointIsInvoked_thenReturnsModelContent() throws Exception {
        when(chatService.reviewCode(anyString(), anyString())).thenReturn("Optimized Code");

        mockMvc.perform(post("/v1/chat/review")
                .param("language", "Java")
                .contentType(MediaType.TEXT_PLAIN)
                .content("class A { }"))
            .andExpect(status().isOk())
            .andExpect(content().string("Optimized Code"));

        verify(chatService).reviewCode("Java", "class A { }");
    }

    @Test
    void whenGroundedEndpointIsInvoked_thenReturnsModelContent() throws Exception {
        when(chatService.searchGroundedPrompt(anyString())).thenReturn("Grounded Response");

        mockMvc.perform(get("/v1/chat/grounded").param("currentEventQuery", "latest match winner"))
            .andExpect(status().isOk())
            .andExpect(content().string("Grounded Response"));

        verify(chatService).searchGroundedPrompt("latest match winner");
    }

    @Test
    void whenStreamEndpointIsInvoked_thenReturnsEventStreamContent() throws Exception {
        when(chatService.streamChatTokens(anyString())).thenReturn(reactor.core.publisher.Flux.just("token-1", "token-2"));

        mockMvc.perform(get("/v1/chat/stream").param("prompt", "Stream tokens"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM))
            .andExpect(content().string("data:token-1\n\ndata:token-2\n\n"));

        verify(chatService).streamChatTokens("Stream tokens");
    }

    @Test
    void contextLoads() {
    }
}
