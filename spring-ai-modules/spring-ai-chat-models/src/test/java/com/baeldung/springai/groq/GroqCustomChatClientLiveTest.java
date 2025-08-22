package com.baeldung.springai.groq;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest()
@ActiveProfiles("customgroq")
public class GroqCustomChatClientLiveTest {
    private final Logger logger = LoggerFactory.getLogger(GroqCustomChatClientLiveTest.class);

    @Autowired
    private CustomGroqChatService customGroqChatService;

    @Test
    void whenCustomGroqClientCalled_thenReturnResponse() {
        String prompt = """
            Context:
            The Eiffel Tower is one of the most famous landmarks
            in Paris, attracting millions of visitors each year.
            Question:
            In which city is the Eiffel Tower located?
            """;
        String response = customGroqChatService.chat(prompt, "llama-3.1-8b-instant", 0.8);

        assertThat(response)
            .isNotNull()
            .isNotEmpty()
            .contains("Paris");
        logger.info("Response from custom Groq client: {}", response);
    }

}
