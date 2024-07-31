package com.baeldung.springvalidator;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.Errors;

@WebMvcTest(UserController.class)
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserValidator userValidator;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void givenValidUser_whenCreateUser_thenReturnsOk() throws Exception {
        User validUser = new User("John Doe", "john@example.com", 30);

        doNothing().when(userValidator).validate(any(), any(), eq("create"));

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)))
            .andExpect(status().isOk())
            .andExpect(content().string("User created successfully!"));
    }

    @Test
    void givenInvalidUser_whenCreateUser_thenReturnsBadRequest() throws Exception {
        User invalidUser = new User("", "", 30);

        doAnswer(invocation -> {
            Errors errors = invocation.getArgument(1);
            errors.rejectValue("name", "name.required", "Name cannot be empty");
            errors.rejectValue("email", "email.required", "Invalid email format");
            return null;
        }).when(userValidator).validate(any(), any(), eq("create"));

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUser)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$[0].defaultMessage").value("Name cannot be empty"))
            .andExpect(jsonPath("$[1].defaultMessage").value("Invalid email format"));
    }

    @Test
    void givenValidUser_whenUpdateUser_thenReturnsOk() throws Exception {
        User validUser = new User("John Doe", "john@example.com", 30);

        doNothing().when(userValidator).validate(any(), any(), eq("update"));

        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)))
            .andExpect(status().isOk())
            .andExpect(content().string("User updated successfully!"));
    }

    @Test
    void givenInvalidUser_whenUpdateUser_thenReturnsBadRequest() throws Exception {
        User invalidUser = new User("", "", 30);

        doAnswer(invocation -> {
            Errors errors = invocation.getArgument(1);
            errors.rejectValue("name", "name.or.email.required", "Name or email cannot be empty");
            return null;
        }).when(userValidator).validate(any(), any(), eq("update"));

        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUser)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$[0].defaultMessage").value("Name or email cannot be empty"));
    }
}