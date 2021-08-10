package com.baeldung.pattern.hexagonal.controller;

import com.baeldung.pattern.hexagonal.config.HexagonalConfig;
import com.baeldung.pattern.hexagonal.domain.model.User;
import com.baeldung.pattern.hexagonal.domain.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = { HexagonalConfig.class, UserController.class })
@AutoConfigureMockMvc
@EnableWebMvc
public class UserControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private static final String email = "email@gmail.com";

    private static final User user = User.builder()
        .email(email)
        .phone("+39333222111")
        .build();

    @Test
    public void whenPostUser_thenServiceWillSave() throws Exception {
        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(user)))
            .andExpect(status().isCreated());

        verify(userService).save(user);
    }

    @Test
    public void whenGetUserByEmail_thenServiceWillFindUser() throws Exception {
        when(userService.getByEmail(email)).thenReturn(user);

        mockMvc.perform(get("/user/" + email).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email", is(user.getEmail())))
            .andExpect(jsonPath("$.phone", is(user.getPhone())));

        verify(userService).getByEmail(email);
    }
}
