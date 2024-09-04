package com.baeldung.gcp.firebase.auth;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.jayway.jsonpath.JsonPath;

import net.bytebuddy.utility.RandomString;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerLiveTest {

    private static final String GET_USER_API_PATH = "/user";
    private static final String CREATE_USER_API_PATH = "/user";
    private static final String LOGIN_USER_API_PATH = "/user/login";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenCreatingUserWithValidDetails_thenUserCreationSucceeds() throws Exception {
        // Set up test data
        String emailId = RandomString.make() + "@baeldung.it";
        String password = RandomString.make();
        String requestBody = String.format("""
            {
                "emailId"  : "%s",
                "password" : "%s"
            }
            """, emailId, password);

        // Invoke API under test
        mockMvc.perform(post(CREATE_USER_API_PATH)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk());
    }

    @Test
    void whenCreatingUserWithExistingEmail_thenUserCreationFails() throws Exception {
        // Set up test data        
        String emailId = RandomString.make() + "@baeldung.it";
        String password = RandomString.make();
        String requestBody = String.format("""
            {
                "emailId"  : "%s",
                "password" : "%s"
            }
            """, emailId, password);

        // Invoke create user API
        mockMvc.perform(post(CREATE_USER_API_PATH)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk());

        // Invoke create user API with same emailId
        mockMvc.perform(post(CREATE_USER_API_PATH)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.status").value(HttpStatus.CONFLICT.value()))
            .andExpect(jsonPath("$.detail").value("Account with given email-id already exists"));
    }

    @Test
    void whenLoginWithValidCredentials_thenTokenReturned() throws Exception {
        // Set up test data        
        String emailId = RandomString.make() + "@baeldung.it";
        String password = RandomString.make();
        String requestBody = String.format("""
            {
                "emailId"  : "%s",
                "password" : "%s"
            }
            """, emailId, password);

        // Invoke create user API
        mockMvc.perform(post(CREATE_USER_API_PATH)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk());

        // Invoke API under test
        mockMvc.perform(post(LOGIN_USER_API_PATH)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void whenLoginWithInvalidCredentials_thenTokenNotReturned() throws Exception {
        // Set up test data        
        String emailId = RandomString.make() + "@baeldung.it";
        String password = RandomString.make();
        String requestBody = String.format("""
            {
                "emailId"  : "%s",
                "password" : "%s"
            }
            """, emailId, password);

        // Invoke API under test
        mockMvc.perform(post(LOGIN_USER_API_PATH)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.status").value(HttpStatus.UNAUTHORIZED.value()))
            .andExpect(jsonPath("$.detail").value("Invalid login credentials provided"));
    }

    @Test
    void whenRetrievingUserWithValidToken_thenDetailsAreReturned() throws Exception {
        // Set up test data        
        String emailId = RandomString.make().toLowerCase() + "@baeldung.it";
        String password = RandomString.make();
        String requestBody = String.format("""
            {
                "emailId"  : "%s",
                "password" : "%s"
            }
            """, emailId, password);

        // Invoke create user API
        mockMvc.perform(post(CREATE_USER_API_PATH)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk());

        // Invoke user login API and extract token
        MvcResult loginResult = mockMvc.perform(post(LOGIN_USER_API_PATH)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andReturn();
        String token = JsonPath.read(loginResult.getResponse().getContentAsString(), "$.token");

        // Invoke API under test
        mockMvc.perform(get(GET_USER_API_PATH)
            .with(csrf())
            .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value(emailId))
            .andExpect(jsonPath("$.emailVerified").value(true));
    }

    @Test
    void whenInvalidToken_thenApiAccessForbidden() throws Exception {
        // Set up test data
        String invalidToken = RandomString.make();

        // Invoke API under test
        mockMvc.perform(get(GET_USER_API_PATH)
            .with(csrf())
            .header("Authorization", "Bearer " + invalidToken))
            .andExpect(status().isForbidden());
    }

}