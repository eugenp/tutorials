package com.baeldung.curltohttprequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class CurlToHttpRequestUnitTest {
    private MockWebServer mockWebServer;

    @BeforeEach
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setBody("{\"message\": \"Success\"}")
            .addHeader("Content-Type", "application/json"));
    }

    @AfterEach
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void givenValidUrl_whenSendPostWithHttpURLConnection_thenReturnSuccessResponse() throws IOException {
        String targetUrl = mockWebServer.url("/api").toString();
        String response = CurlToHttpRequest.sendPostWithHttpURLConnection(targetUrl);

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals("{\"message\": \"Success\"}", response);
    }

    @Test
    public void givenValidUrl_whenSendPostWithApacheHttpClient_thenReturnSuccessResponse() throws IOException {
        String targetUrl = mockWebServer.url("/api").toString();
        String response = CurlToHttpRequest.sendPostWithApacheHttpClient(targetUrl);

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals("{\"message\": \"Success\"}", response);
    }

    @Test
    public void givenValidUrl_whenSendPostWithOkHttp_thenReturnSuccessResponse() throws IOException {
        String targetUrl = mockWebServer.url("/api").toString();
        String response = CurlToHttpRequest.sendPostWithOkHttp(targetUrl);

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals("{\"message\": \"Success\"}", response);
    }
}
