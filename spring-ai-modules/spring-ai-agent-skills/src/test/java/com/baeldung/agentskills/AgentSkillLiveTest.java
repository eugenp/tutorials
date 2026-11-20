package com.baeldung.agentskills;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".*")
class AgentSkillLiveTest {

    private static final String API_PATH = "/chat";
    private static final String REQUEST_BODY_TEMPLATE = """
        {
            "question": "%s"
        }
        """;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenArticleSummaryRequested_thenResponseContainsExpectedSections() throws Exception {
        String requestBody = REQUEST_BODY_TEMPLATE.formatted(
            "Can you summarize the following article: https://www.baeldung.com/sample-non-existing-article"
        );

        MvcResult result = mockMvc
            .perform(post(API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
            )
            .andExpect(status().isOk())
            .andReturn();

        String response = result
            .getResponse()
            .getContentAsString()
            .toLowerCase();

        assertThat(response)
            .contains("tl;dr", "key points", "bottom line");
    }

}