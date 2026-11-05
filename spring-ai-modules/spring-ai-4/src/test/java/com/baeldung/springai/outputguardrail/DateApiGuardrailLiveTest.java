package com.baeldung.springai.outputguardrail;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("outputguardrail")
class DateApiGuardrailLiveTest {

    private static final String FAILURE_RESPONSE = "Use java.time instead.";

    @Autowired
    private ChatClient chatClient;

    @Test
    void givenLegacyApiPrompt_whenCallingTheModel_thenItIsBlocked() {
        String answer = chatClient.prompt()
          .user("How do I use SimpleDateFormat?")
          .call()
          .content();

        assertThat(answer).isEqualTo(FAILURE_RESPONSE);
    }

    @Test
    void givenJava7Question_whenCallingTheModel_thenAnswerIsBlocked() {
        String answer = chatClient.prompt()
          .user("How did we parse date strings in Java 7?")
          .call()
          .content();

        assertThat(answer).isEqualTo(FAILURE_RESPONSE);
    }

    @Test
    void givenModernQuestion_whenCallingTheModel_thenAnswerIsReturned() {
        String answer = chatClient.prompt()
          .user("How do I get today's date in Java?")
          .call()
          .content();

        assertThat(answer).isNotEqualTo(FAILURE_RESPONSE);
    }
}
