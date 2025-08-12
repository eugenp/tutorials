package com.baeldung.springai.groq;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("groq")
public class GroqAutoconfiguredChatClientLiveTest {
    final Logger logger = LoggerFactory.getLogger(GroqAutoconfiguredChatClientLiveTest.class);
    @Autowired
    private GroqChatService groqChatService;

    @Test
    void whenCallOpenAIClient_thenReturnResponseFromGroq() {

        String prompt = """
            Context:
            Support Ticket #98765:
            Product: XYZ Wireless Mouse
            Issue Description: The mouse connects intermittently to my laptop.
            I've tried changing batteries and reinstalling drivers,
            but the cursor still freezes randomly for a few seconds before resuming normal movement.
            It affects productivity significantly.
            Question:
            Based on the support ticket, what is the primary technical issue
            the user is experiencing with their 'XYZ Wireless Mouse'?;
            """;
        String response = groqChatService.chat(prompt);

        assertThat(response.toLowerCase()).isNotNull()
            .isNotEmpty()
            .containsAnyOf("laptop", "mouse", "connect");

        ChatOptions openAiChatOptions = groqChatService.getChatOptions();
        String model = openAiChatOptions.getModel();
        Double temperature = openAiChatOptions.getTemperature();

        assertThat(openAiChatOptions).isInstanceOf(OpenAiChatOptions.class);
        assertThat(model).isEqualTo("llama-3.3-70b-versatile");
        assertThat(temperature).isEqualTo(Double.valueOf(0.7));
        logger.info("Response from Groq:{}", response);
    }

}
