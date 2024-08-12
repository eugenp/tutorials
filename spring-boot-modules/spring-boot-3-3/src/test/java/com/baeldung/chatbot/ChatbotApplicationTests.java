package com.baeldung.chatbot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ChatbotApplicationTests {

    @Test
    void contextLoads() {
        assertThat(true).isTrue();
    }
}
