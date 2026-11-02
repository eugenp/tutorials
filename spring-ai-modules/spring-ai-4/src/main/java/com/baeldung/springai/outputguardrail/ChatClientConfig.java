package com.baeldung.springai.outputguardrail;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    private static final String SYSTEM_PROMPT = """
        You are a Java coding assistant.
        Answer in two sentences with one short code snippet.
        """;

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        SafeGuardAdvisor inputGuard = SafeGuardAdvisor.builder()
          .sensitiveWords(List.of("SimpleDateFormat", "java.util.Date"))
          .failureResponse("Use java.time instead.")
          .build();

        OutputSafeGuardAdvisor outputGuard = OutputSafeGuardAdvisor.builder()
          .sensitiveWords("DateFormat", "java.util.Date",
            "Calendar", "Timestamp")
          .failureResponse("Use java.time instead.")
          .build();

        return builder.defaultSystem(SYSTEM_PROMPT)
          .defaultAdvisors(inputGuard, outputGuard)
          .build();
    }
}
