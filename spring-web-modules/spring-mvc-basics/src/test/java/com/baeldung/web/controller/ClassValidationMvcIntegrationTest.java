package com.baeldung.web.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class ClassValidationMvcIntegrationTest {
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new NewUserController())
            .build();
    }

    @Test
    public void givenMatchingEmailPassword_whenPostNewUserForm_thenOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/user")
            .accept(MediaType.TEXT_HTML)
            .param("email", "john@yahoo.com")
            .param("verifyEmail", "john@yahoo.com")
            .param("password", "pass")
            .param("verifyPassword", "pass"))
            .andExpect(model().attribute("message", "Valid form"))
            .andExpect(view().name("userHome"))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    public void givenNotMatchingEmailPassword_whenPostNewUserForm_thenOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/user")
            .accept(MediaType.TEXT_HTML)
            .param("email", "john@yahoo.com")
            .param("verifyEmail", "john@yahoo.commmm")
            .param("password", "pass")
            .param("verifyPassword", "passsss"))
            .andExpect(model().errorCount(2))
            .andExpect(view().name("userHome"))
            .andExpect(status().isOk())
            .andDo(print());
    }
}
