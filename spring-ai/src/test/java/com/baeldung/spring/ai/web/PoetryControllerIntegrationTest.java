package com.baeldung.spring.ai.web;

import com.baeldung.spring.ai.dto.PoetryDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.OpenAiError;
import com.theokanning.openai.OpenAiHttpException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ai.client.AiClient;
import org.springframework.ai.client.AiResponse;
import org.springframework.ai.client.Generation;
import org.springframework.ai.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class PoetryControllerIntegrationTest {

    public static final String GENERATED_HAIKU = "generated haiku";
    public static final String TEST_TITLE = "test_title";
    public static final String SOME_POETRY = "some poetry";
    public static final String TEST_GENRE = "test_genre";
    public static final String TEST_THEME = "test_theme";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AiClient aiClient;

    @Test
    public void givenGetCatHaiku_whenCallingAiClient_thenCorrect() throws Exception {
        when(aiClient.generate(anyString())).thenReturn(GENERATED_HAIKU);
        mockMvc.perform(get("/ai/cathaiku"))
                .andExpect(status().isOk())
                .andExpect(content().string(GENERATED_HAIKU));
    }

    @Test
    public void givenGetPoetryWithGenreAndTheme_whenCallingAiClient_thenCorrect() throws Exception {
        PoetryDto poetryDto = new PoetryDto(
                TEST_TITLE,
                SOME_POETRY,
                TEST_GENRE,
                TEST_THEME
        );
        when(aiClient.generate((Prompt) any(Prompt.class))).thenReturn(
                new AiResponse(List.of(new Generation(objectMapper.writeValueAsString(poetryDto)))));
        String genre = "lyric";
        String theme = "coffee";
        mockMvc.perform(get("/ai/poetry?genre={genre}&theme={theme}", genre, theme))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genre").value(poetryDto.genre()))
                .andExpect(jsonPath("$.theme").value(poetryDto.theme()))
                .andExpect(jsonPath("$.poetry").value(poetryDto.poetry()))
                .andExpect(jsonPath("$.title").value(poetryDto.title()));
    }

    @Test
    public void givenGetCatHaiku_whenCallingAiClientWithIncorrectToken_thenUnauthorized() throws Exception {
        when(aiClient.generate(anyString())).thenThrow(new OpenAiHttpException(
                new OpenAiError(new OpenAiError.OpenAiErrorDetails()),
                new Exception(),
                401));
        mockMvc.perform(get("/ai/cathaiku"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.title").value(ExceptionTranslator.OPEN_AI_CLIENT_RAISED_EXCEPTION));
    }
}
