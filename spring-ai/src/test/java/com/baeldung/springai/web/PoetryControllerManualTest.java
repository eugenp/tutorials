package com.baeldung.springai.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class PoetryControllerManualTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier("openAiChatModel")
    private ChatModel aiClient;

    @Test
    public void givenGetCatHaiku_whenCallingAiClient_thenCorrect() throws Exception {
        mockMvc.perform(get("/ai/cathaiku"))
          .andExpect(status().isOk())
          .andExpect(content().string(containsStringIgnoringCase("cat")));
    }

    @Test
    public void givenGetPoetryWithGenreAndTheme_whenCallingAiClient_thenCorrect() throws Exception {
        String genre = "lyric";
        String theme = "coffee";
        mockMvc.perform(get("/ai/poetry?genre={genre}&theme={theme}", genre, theme))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.genre").value(containsStringIgnoringCase(genre)))
          .andExpect(jsonPath("$.theme").value(containsStringIgnoringCase(theme)))
          .andExpect(jsonPath("$.poetry").isNotEmpty())
          .andExpect(jsonPath("$.title").exists());
    }
}

