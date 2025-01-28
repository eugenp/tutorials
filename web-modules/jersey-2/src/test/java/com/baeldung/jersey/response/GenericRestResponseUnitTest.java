package com.baeldung.jersey.response;

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
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("GenericRestResponse Tests")
public class GenericRestResponseUnitTest {

    @Mock private Client client;
    @Mock private WebTarget webTarget;
    @Mock private Response response;
    @Mock private Invocation.Builder builder;
    @Mock private Logger logger;

    private GenericRestResponse genericRestResponse;

    private static final String TEST_URL = "https://api.example.com/data";
    private static final String TEST_PAYLOAD = "{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\"}";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        genericRestResponse = new GenericRestResponse(client, logger);

        // Common mock setup
        when(client.target(TEST_URL)).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.post(any(Entity.class))).thenReturn(response);
    }

    @Test
    @DisplayName("Should successfully process OK response")
    void givenOkResponse_whenSendingRequest_thenShouldSuccessfullyProcess() {
        // Given
        String expectedResponse = "{\"status\":\"success\"}";
        when(response.getStatus()).thenReturn(Response.Status.OK.getStatusCode());
        when(response.readEntity(String.class)).thenReturn(expectedResponse);

        // When
        genericRestResponse.sendRequest(TEST_URL, TEST_PAYLOAD);

        // Then
        verify(logger).info(contains(expectedResponse));
        verify(response).close();
        verify(client).close();
    }

    @Test
    @DisplayName("Should handle failed response")
    void givenFailedResponse_whenSendingRequest_thenShouldHandleFailedResponse() {
        // Given
        when(response.getStatus()).thenReturn(Response.Status.BAD_REQUEST.getStatusCode());

        // When
        genericRestResponse.sendRequest(TEST_URL, TEST_PAYLOAD);

        // Then
        verify(logger).error("Failed to get a successful response");
        verify(response).close();
        verify(client).close();
    }

    @Test
    @DisplayName("Given runtime exception, when sending request, then handle exception appropriately")
    void givenRuntimeException_whenSendingRequest_thenHandleException() {
        // Given
        RuntimeException testException = new RuntimeException("Test exception");
        when(response.getStatus()).thenThrow(testException);

        // When
        genericRestResponse.sendRequest(TEST_URL, TEST_PAYLOAD);

        // Then
        verify(logger).error(eq("Error processing response"), eq(testException));
        verify(response).close();
        verify(client).close();
    }

    @Test
    @DisplayName("Given empty response body, when sending request, then process empty response")
    void givenEmptyResponseBody_whenSendingRequest_thenProcessEmptyResponse() {
        // Given
        when(response.getStatus()).thenReturn(Response.Status.OK.getStatusCode());
        when(response.readEntity(String.class)).thenReturn("");

        // When
        genericRestResponse.sendRequest(TEST_URL, TEST_PAYLOAD);

        // Then
        verify(logger).info(contains(""));
        verify(response).close();
        verify(client).close();
    }

    @Test
    @DisplayName("Should handle malformed response")
    void givenOkResponse_whenMalformedResponse_thenShouldHandle() {
        // Given
        when(response.getStatus()).thenReturn(Response.Status.OK.getStatusCode());
        when(response.readEntity(String.class)).thenThrow(new RuntimeException("Malformed response"));

        // When
        genericRestResponse.sendRequest(TEST_URL, TEST_PAYLOAD);

        // Then
        verify(logger).error(eq("Error processing response"), any(RuntimeException.class));
        verify(response).close();
        verify(client).close();
    }
}