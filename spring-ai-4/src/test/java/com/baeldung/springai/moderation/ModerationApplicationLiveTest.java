package com.baeldung.springai.moderation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration
@SpringBootTest
@ActiveProfiles("moderation")
class ModerationApplicationLiveTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenTextWithoutViolation_whenModerating_thenNoCategoryViolationsDetected() throws Exception {
        String moderationResponse = mockMvc.perform(post("/moderate")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"text\": \"Please review me\"}"))
          .andExpect(status().isOk())
          .andReturn()
          .getResponse()
          .getContentAsString();

        assertThat(moderationResponse).contains("No category violations detected");
    }

    @Test
    void givenHarassingText_whenModerating_thenHarassmentCategoryShouldBeFlagged() throws Exception {
        String moderationResponse = mockMvc.perform(post("/moderate")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"text\": \"You're really Bad Person! I don't like you!\"}"))
          .andExpect(status().isOk())
          .andReturn()
          .getResponse()
          .getContentAsString();

        assertThat(moderationResponse).contains("Violated categories: Harassment");
    }

    @Test
    void givenTextViolatingMultipleCategories_whenModerating_thenAllCategoriesShouldBeFlagged() throws Exception {
        String moderationResponse = mockMvc.perform(post("/moderate")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"text\": \"I hate you and I will hurt you!\"}"))
          .andExpect(status().isOk())
          .andReturn()
          .getResponse()
          .getContentAsString();

        assertThat(moderationResponse).contains("Violated categories: Harassment, Harassment/Threatening, Violence");
    }
}