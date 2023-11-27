package com.baeldung.spring.ai.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ai.client.AiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static java.lang.String.format;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExceptionTranslatorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AiClient aiClient;

    @DynamicPropertySource
    static void setupTestcontainerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.ai.openai.api-key", () -> "incorrect_token");
    }

    @Test
    public void givenGetCatHaiku_whenCallingAiClientWithIncorrectToken_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/ai/cathaiku"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.title").value(ExceptionTranslator.OPEN_AI_CLIENT_RAISED_EXCEPTION));
    }
}
