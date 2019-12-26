package com.baeldung.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class SimpleBookControllerIntegrationTest {

    private MockMvc mockMvc;
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new SimpleBookController()).build();
    }

    @Test
    public void givenBookId_whenMockMVC_thenVerifyResponse() throws Exception {
        this.mockMvc
            .perform(get("/books/42"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(CONTENT_TYPE))
            .andExpect(jsonPath("$.id").value(42));
    }

}

