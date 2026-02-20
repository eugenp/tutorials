package com.baeldung.springai;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class QualityCheckAdvisorTests {

    @MockitoBean
    ChatModel chatModel;

    @Autowired
    ChatClient.Builder chatClientBuilder;

    @Test
    void givenShortFirstResponse_whenAdvised_thenRetriesAndReturnsLongResponse() {
        var shortResponse = "Too brief.";
        var longResponse = "S".repeat(250);

        when(chatModel.call(any(Prompt.class)))
                .thenReturn(createChatResponse(shortResponse))
                .thenReturn(createChatResponse(longResponse));

        var chatClient = chatClientBuilder
                .defaultAdvisors(new QualityCheckAdvisor())
                .build();

        String result = chatClient.prompt()
                .user("Explain the SOLID principles.")
                .call()
                .content();

        assertThat(result).hasSize(250);
        verify(chatModel, times(2)).call(any(Prompt.class));
    }

    private ChatResponse createChatResponse(String content) {
        return new ChatResponse(
                List.of(new Generation(new AssistantMessage(content)))
        );
    }
}