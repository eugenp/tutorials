package com.baeldung.jersey.response;

import com.baeldung.jersey.model.User;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("JsonResponse Tests")
class JsonResponseUnitTest {

    @Mock private Client client;
    @Mock private WebTarget webTarget;
    @Mock private Invocation.Builder builder;
    @Mock private Response response;
    @Mock private Logger logger;

    private JsonResponse jsonResponse;
    private static final String BASE_URL = "https://api.example.com/user";

    @BeforeEach
    void setUp() {
        jsonResponse = new JsonResponse(client, logger, BASE_URL);

        // Common mock setup
        when(client.target(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.post(any(Entity.class))).thenReturn(response);
    }

    @Test
    @DisplayName("Should successfully fetch user data")
    void whenValidUserId_thenReturnUserData() {
        // Given
        User expectedUser = new User(1, "John Doe");
        when(response.getStatus()).thenReturn(Response.Status.OK.getStatusCode());
        when(response.readEntity(User.class)).thenReturn(expectedUser);

        // When
        User actualUser = jsonResponse.fetchUserData(1);

        // Then
        assertNotNull(actualUser);
        assertEquals(expectedUser.getId(), actualUser.getId());
        assertEquals(expectedUser.getName(), actualUser.getName());
        verify(response).close();
    }

    @Test
    @DisplayName("Should handle non-200 response")
    void whenNon200Response_thenReturnNull() {
        // Given
        when(response.getStatus()).thenReturn(Response.Status.BAD_REQUEST.getStatusCode());

        // When
        User user = jsonResponse.fetchUserData(1);

        // Then
        assertNull(user);
        verify(logger).error(eq("Failed to get user data. Status: {}"), eq(400));
        verify(response).close();
    }

    @Test
    @DisplayName("Should handle exception during processing")
    void whenExceptionOccurs_thenReturnNull() {
        // Given
        when(response.getStatus()).thenThrow(new RuntimeException("Test exception"));

        // When
        User user = jsonResponse.fetchUserData(1);

        // Then
        assertNull(user);
        verify(logger).error(eq("Error processing user data"), any(RuntimeException.class));
        verify(response).close();
    }
}