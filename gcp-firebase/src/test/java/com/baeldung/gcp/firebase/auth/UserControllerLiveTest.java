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
    private static final String REFRESH_TOKEN_API_PATH = "/user/refresh-token";

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
            .andExpect(jsonPath("$.idToken").exists());
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
        String token = JsonPath.read(loginResult.getResponse().getContentAsString(), "$.idToken");

        // Invoke API under test
        mockMvc.perform(get(GET_USER_API_PATH)
            .with(csrf())
            .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value(emailId))
            .andExpect(jsonPath("$.emailVerified").value(true));
    }

    @Test
    void whenExchangingValidRefreshToken_thenNewIdTokenReturned() throws Exception {
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

        // Invoke user login API and extract refresh token
        MvcResult loginResult = mockMvc.perform(post(LOGIN_USER_API_PATH)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andReturn();
        String refreshToken = JsonPath.read(loginResult.getResponse().getContentAsString(), "$.refreshToken");

        // Invoke API under test and extract ID token
        requestBody = String.format("""
            {
                "refreshToken" : "%s"
            }
            """, refreshToken);
        MvcResult refreshTokenResult = mockMvc.perform(post(REFRESH_TOKEN_API_PATH)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id_token").exists())
            .andReturn();
        String idToken = JsonPath.read(refreshTokenResult.getResponse().getContentAsString(), "$.id_token");
        
        // Verify ID token invokes private API
        mockMvc.perform(get(GET_USER_API_PATH)
            .with(csrf())
            .header("Authorization", "Bearer " + idToken))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value(emailId))
            .andExpect(jsonPath("$.emailVerified").value(true));
    }

    @Test
    void whenExchangingInvalidRefreshToken_thenNewIdTokenNotReturned() throws Exception {
        // Set up test data        
        String invalidRefreshToken = RandomString.make();
        String requestBody = String.format("""
            {
                "refreshToken"  : "%s"
            }
            """, invalidRefreshToken);
        
        // Invoke API under test
         mockMvc.perform(post(REFRESH_TOKEN_API_PATH)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.status").value(HttpStatus.FORBIDDEN.value()))
            .andExpect(jsonPath("$.detail").value("Invalid refresh token provided"));
    }

    @Test
    void whenInvalidToken_thenApiAccessDenied() throws Exception {
        // Set up test data
        String invalidToken = RandomString.make();

        // Invoke API under test
        mockMvc.perform(get(GET_USER_API_PATH)
            .with(csrf())
            .header("Authorization", "Bearer " + invalidToken))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.status").value(HttpStatus.UNAUTHORIZED.value()))
            .andExpect(jsonPath("$.detail").value("Authentication failure: Token missing, invalid or expired"));;
    }

}