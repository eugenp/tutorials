package com.baeldung.automemorytools;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled // Uncomment after setting up ollama server, or any other ai-agent
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ApplicationTest {

    private static final String API_PATH = "/chat-with-memory";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenArticleSummaryRequested_thenResponseContainsExpectedSections() throws Exception {
        String requestBody = """
                Hello! My name is Awesome. I'm a backend engineer and I prefer short answers.
                Please remember this info. Also remember that i have to write a very important article about AI.
                Who are you?
                """;
        String conversationId = UUID.randomUUID().toString();

        MvcResult result = mockMvc
                .perform(post(API_PATH)
                        .header("X-Conversation-ID", conversationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isOk())
                .andReturn();

        String response = result
                .getResponse()
                .getContentAsString()
                .toLowerCase();

        System.out.println(response);

        requestBody = "What do u know about me?";

        result = mockMvc
                .perform(post(API_PATH)
                        .header("X-Conversation-ID", conversationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isOk())
                .andReturn();

        response = result
                .getResponse()
                .getContentAsString()
                .toLowerCase();

        System.out.println(response);

        assertThat(response).contains("backend engineer", "article");
    }
}
