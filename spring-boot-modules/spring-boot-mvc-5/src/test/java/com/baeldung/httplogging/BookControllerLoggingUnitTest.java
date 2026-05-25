package com.baeldung.httplogging;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(OutputCaptureExtension.class)
class BookControllerLoggingTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenCreateBook_thenRequestAndResponseAreLogged(CapturedOutput output) throws Exception {
        String requestBody = """
                { "title": "Spring in Action", "author": "Craig Walls" }
                """;

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());

        assertThat(output).contains("Incoming POST /api/books");
        assertThat(output).contains("HTTP POST /api/books status=201");
        assertThat(output).contains("\"title\":\"Spring in Action\"");
        assertThat(output).contains("\"author\":\"Craig Walls\"");
    }
}
