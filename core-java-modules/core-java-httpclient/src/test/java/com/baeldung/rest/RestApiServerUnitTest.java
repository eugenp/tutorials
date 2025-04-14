package com.baeldung.rest;

import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestApiServerUnitTest {

    private RestApiServer restApiServer;
    private HttpExchange exchange;
    private ByteArrayOutputStream responseStream;

    @BeforeEach
    void setUp() {
        restApiServer = new RestApiServer();
        exchange = mock(HttpExchange.class);
        responseStream = new ByteArrayOutputStream();
    }

    private void mockRequest(String method, String body) throws IOException {
        InputStream requestBody = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
        when(exchange.getRequestMethod()).thenReturn(method);
        when(exchange.getRequestBody()).thenReturn(requestBody);
        when(exchange.getResponseBody()).thenReturn(responseStream);
        when(exchange.getResponseHeaders()).thenReturn(Mockito.mock(com.sun.net.httpserver.Headers.class));
    }

    @Test
    void givenEmptyUserList_whenGetRequest_thenReturnEmptyList() throws IOException {
        mockRequest("GET", "");
        restApiServer.handle(exchange);
        String response = responseStream.toString(StandardCharsets.UTF_8);
        assertEquals("[]", response.trim());
    }

    @Test
    void givenValidUser_whenPostRequest_thenUserIsAdded() throws IOException {
        mockRequest("POST", "JohnDoe");
        restApiServer.handle(exchange);
        String response = responseStream.toString(StandardCharsets.UTF_8);
        assertTrue(response.contains("User added: JohnDoe"));
    }

    @Test
    void givenEmptyBody_whenPostRequest_thenReturnInvalidUserDataMessage() throws IOException {
        mockRequest("POST", "");
        restApiServer.handle(exchange);
        String response = responseStream.toString(StandardCharsets.UTF_8);
        assertTrue(response.contains("Invalid user data"));
    }

    @Test
    void givenValidIndex_whenPutRequest_thenUserIsUpdated() throws IOException {
        mockRequest("POST", "JohnDoe");
        restApiServer.handle(exchange);

        mockRequest("PUT", "0:JaneDoe");
        restApiServer.handle(exchange);
        String response = responseStream.toString(StandardCharsets.UTF_8);
        assertTrue(response.contains("User updated: JaneDoe"));
    }

    @Test
    void givenInvalidIndex_whenPutRequest_thenReturnUserNotFoundMessage() throws IOException {
        mockRequest("PUT", "10:NewUser");
        restApiServer.handle(exchange);
        String response = responseStream.toString(StandardCharsets.UTF_8);
        assertTrue(response.contains("User not found"));
    }

    @Test
    void givenValidIndex_whenDeleteRequest_thenUserIsDeleted() throws IOException {
        mockRequest("POST", "JohnDoe");
        restApiServer.handle(exchange);

        mockRequest("DELETE", "0");
        restApiServer.handle(exchange);
        String response = responseStream.toString(StandardCharsets.UTF_8);
        assertTrue(response.contains("User deleted: JohnDoe"));
    }

    @Test
    void givenInvalidIndex_whenDeleteRequest_thenReturnUserNotFoundMessage() throws IOException {
        mockRequest("DELETE", "5");
        restApiServer.handle(exchange);
        String response = responseStream.toString(StandardCharsets.UTF_8);
        assertTrue(response.contains("User not found"));
    }
}
