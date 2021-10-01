package com.baeldung.httpfirewall.api;

import com.baeldung.httpfirewall.model.User;
import com.baeldung.httpfirewall.service.UserServiceImpl;
import com.baeldung.httpfirewall.utility.UserTestUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@WebMvcTest
@AutoConfigureMockMvc
@DisplayName("User API Unit Tests")
class UserApiUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Test to Check Authentication")
    void whenNoAuthentication_thenThrow401() throws Exception {
        // @formatter:off
        MvcResult result = mockMvc
          .perform(post("/api/v1/users")
            .content(objectMapper.writeValueAsString(UserTestUtility.createUser()))
            .contentType("application/json"))
          .andReturn();
        assertEquals(HttpStatus.UNAUTHORIZED.value(), result.getResponse().getStatus());
        // @formatter:off
    }

    @Test
    @WithMockUser
    @DisplayName("Test Malicious URL")
    void givenCredentials_whenMaliciousUrl_thenThrowRequestRejectedException() throws Exception {
        // @formatter:off
        MvcResult result = mockMvc
          .perform(post("/api/v1\\users")
            .content(objectMapper.writeValueAsString(UserTestUtility.createUser()))
            .contentType("application/json"))
          .andDo(print())
          .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        // @formatter:on
    }

    @Test
    @WithMockUser
    @DisplayName("Test User Create")
    void givenCredentials_whenHttpPost_thenReturn201() throws Exception {
        // @formatter:off
        doNothing().when(userService).saveUser(new User());

        MvcResult result=mockMvc
          .perform(post("/api/v1/users")
            .content(objectMapper.writeValueAsString(UserTestUtility.createUser()))
            .contentType("application/json"))
          .andDo(print())
          .andExpect(header().exists("Location")).andReturn();
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        // @formatter:on
    }

    @Test
    @WithMockUser
    @DisplayName("Test User Create Without ID")
    void givenCredentials_whenHttpPostWithId_thenReturn201() throws Exception {
        // @formatter:off
       doNothing().when(userService).saveUser(new User());

        MvcResult result = mockMvc
          .perform(post("/api/v1/users")
            .content(objectMapper.writeValueAsString(UserTestUtility.createUserWithoutId()))
            .contentType("application/json"))
          .andDo(print())
          .andExpect(header().exists("Location")).andReturn();
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        // @formatter:on
    }

    @Test
    @WithMockUser
    @DisplayName("Test Get User")
    void givenCredentials_whenHttpGetWithId_thenReturnUser() throws Exception {
        String userId = "1";
        // @formatter:off
        when(userService.findById("1")).thenReturn(UserTestUtility.createUserWithId(userId));

        MvcResult result = mockMvc
          .perform(get("/api/v1/users/"+userId)
            .accept("application/json"))
          .andDo(print())
          .andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertNotNull(result.getResponse());
        assertEquals("jhondoe",objectMapper.readValue(result.getResponse().getContentAsString(),  User.class).getUsername());

        // @formatter:on
    }

    @Test
    @WithMockUser
    @DisplayName("Test Get All Users")
    void givenCredentials_whenHttpGetWithoutId_thenReturnAllUsers() throws Exception {
        // @formatter:off
        when(userService.findAll()).thenReturn(UserTestUtility.createUsers());

        MvcResult result = mockMvc
          .perform(get("/api/v1/users/")
            .accept("application/json"))
          .andDo(print())
          .andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertNotNull(result.getResponse());
        assertTrue(result.getResponse().getContentAsString().contains("jane.doe"));
        // @formatter:on
    }

    @Test
    @WithMockUser
    @DisplayName("Test Delete a User")
    void givenCredentials_whenHttpDelete_thenDeleteUser() throws Exception {
        String userId = "1";
        doNothing().when(userService).deleteUser(userId);
        // @formatter:off
        MvcResult result = mockMvc
          .perform(delete("/api/v1/users/"+userId)
            .accept("application/json"))
          .andDo(print())
          .andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertNotNull(result.getResponse());
        assertTrue(result.getResponse().getContentAsString().contains("The user has been deleted successfully"));
        // @formatter:on
    }

}
