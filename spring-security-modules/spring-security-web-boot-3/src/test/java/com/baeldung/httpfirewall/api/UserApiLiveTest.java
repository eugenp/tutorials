package com.baeldung.httpfirewall.api;

import com.baeldung.httpfirewall.model.User;

import com.baeldung.httpfirewall.service.UserServiceImpl;
import com.baeldung.httpfirewall.utility.UserTestUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("User API Live Tests")
class UserApiLiveTest {

    private final String userId = "1";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() throws Exception {
        //@formatter:off
        mockMvc
          .perform(post("/api/v1/users")
            .content(objectMapper.writeValueAsString(UserTestUtility.createUserWithId(userId)))
            .contentType("application/json"));
        //@formatter:on
    }

    @Test
    @WithMockUser
    @DisplayName("LiveTest User Creation")
    void givenCredentials_whenHttpPost_thenReturn201() throws Exception {
        // @formatter:off
        MvcResult result = mockMvc
          .perform(post("/api/v1/users")
            .content(objectMapper.writeValueAsString(UserTestUtility.createUserWithId("200")))
            .contentType("application/json"))
          .andDo(print())
          .andExpect(header().exists("Location")).andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        // @formatter:on
        mockMvc.perform(delete("/api/v1/users/" + 200).contentType("application/json"));
    }

    @Test
    @WithMockUser
    @DisplayName("LiveTest Get User")
    void givenCredentials_whenHttpGetById_thenReturnUser() throws Exception {
        // @formatter:off
        MvcResult result=mockMvc
          .perform(get("/api/v1/users/"+userId)
            .contentType("application/json")).andReturn();
        // @formatter:on
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertNotNull(result.getResponse());
        assertEquals(userId, objectMapper.readValue(result.getResponse().getContentAsString(), User.class).getId());
    }

    @Test
    @WithMockUser
    @DisplayName("LiveTest Get All Users")
    void givenCredentials_whenHttpGet_thenReturnAllUsers() throws Exception {
        // @formatter:off
        MvcResult result=mockMvc
          .perform(get("/api/v1/users/")
            .contentType("application/json")).andReturn();
        // @formatter:on
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());

        List<User> users = objectMapper.readValue(result.getResponse().getContentAsString(), objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));

        assertEquals(1, users.size());
    }

    @Test
    @WithMockUser
    @DisplayName("LiveTest Delete User")
    void givenCredentials_whenHttpDelete_thenDeleteUser() throws Exception {
        // @formatter:off
        MvcResult result=mockMvc
          .perform(delete("/api/v1/users/"+userId)
            .contentType("application/json")).andReturn();
        // @formatter:on
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
    }

}
