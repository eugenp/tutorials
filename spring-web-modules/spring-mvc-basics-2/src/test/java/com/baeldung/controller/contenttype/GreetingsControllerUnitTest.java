package com.baeldung.controller.contenttype;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.baeldung.spring.controller.GreetingsController;

public class GreetingsControllerUnitTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new GreetingsController())
            .build();
    }

    @Test
    public void givenReturnTypeIsString_whenJacksonOnClasspath_thenDefaultContentTypeIsJSON() throws Exception {

        // Given
        String expectedMimeType = "application/json";

        // Then
        String actualMimeType = this.mockMvc.perform(MockMvcRequestBuilders.get("/greetings-with-response-body", 1))
            .andReturn()
            .getResponse()
            .getContentType();

        assertEquals(expectedMimeType, actualMimeType);

    }

    @Test
    public void givenReturnTypeIsResponseEntity_thenDefaultContentTypeIsJSON() throws Exception {
        
        // Given
        String expectedMimeType = "application/json";

        // Then
        String actualMimeType = this.mockMvc.perform(MockMvcRequestBuilders.get("/greetings-with-response-entity", 1))
            .andReturn()
            .getResponse()
            .getContentType();

        assertEquals(expectedMimeType, actualMimeType);
    }
}