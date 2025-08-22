package com.baeldung.springai.memory;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("memory")
class ChatServiceLiveTest {

    private static final String PROMPT_1ST = "Tell me a joke";
    private static final String PROMPT_2ND = "Tell me another one";

    @Autowired
    private ChatMemory chatMemory;

    @Autowired
    private ChatService chatService;

    @Test
    void whenChatServiceIsCalledTwice_thenChatMemoryHasCorrectNumberOfEntries() {
        String conversationId = chatService.getConversationId();

        // 1st request
        String response1 = chatService.chat(PROMPT_1ST);
        assertThat(response1).isNotEmpty();
        assertThat(chatMemory.get(conversationId)).hasSize(2);

        // 2nd request
        String response2 = chatService.chat(PROMPT_2ND);
        assertThat(response2).isNotEmpty();
        assertThat(chatMemory.get(conversationId)).hasSize(4);
    }

}