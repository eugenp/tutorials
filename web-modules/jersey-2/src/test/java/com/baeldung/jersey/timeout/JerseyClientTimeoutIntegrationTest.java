package com.baeldung.jersey.timeout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.baeldung.jersey.timeout.client.JerseyTimeoutClient;
import com.baeldung.jersey.timeout.server.JerseyTimeoutServerApp;

import jakarta.ws.rs.ProcessingException;

class JerseyClientTimeoutIntegrationTest {

    private static final String CONNECT_TIMED_OUT = "Connect timed out";
    private static final String READ_TIMED_OUT = "Read timed out";

    private static final URI BASE = JerseyTimeoutServerApp.BASE_URI;
    private static final String CORRECT_ENDPOINT = BASE + "/timeout";
    private static final String INCORRECT_ENDPOINT = BASE.toString()
        .replace(BASE.getHost(), "10.255.255.1");

    private static HttpServer server;
    private JerseyTimeoutClient readTimeoutClient = new JerseyTimeoutClient(CORRECT_ENDPOINT);
    private JerseyTimeoutClient connectTimeoutClient = new JerseyTimeoutClient(INCORRECT_ENDPOINT);

    @BeforeAll
    static void beforeAllTests() throws IOException {
        server = JerseyTimeoutServerApp.start();
    }

    @AfterAll
    static void afterAllTests() {
        server.shutdownNow();
    }

    private void assertTimeout(String message, Executable executable) {
        ProcessingException exception = assertThrows(ProcessingException.class, executable);

        Throwable cause = exception.getCause();
        assertInstanceOf(SocketTimeoutException.class, cause);

        assertEquals(message, cause.getMessage());
    }

    @Test
    void givenCorrectEndpoint_whenClientBuilderAndSlowServer_thenReadTimeout() {
        assertTimeout(READ_TIMED_OUT, readTimeoutClient::viaClientBuilder);
    }

    @Test
    void givenIncorrectEndpoint_whenClientBuilder_thenConnectTimeout() {
        assertTimeout(CONNECT_TIMED_OUT, connectTimeoutClient::viaClientBuilder);
    }

    @Test
    void givenCorrectEndpoint_whenClientConfigAndSlowServer_thenReadTimeout() {
        assertTimeout(READ_TIMED_OUT, readTimeoutClient::viaClientConfig);
    }

    @Test
    void givenIncorrectEndpoint_whenClientConfig_thenConnectTimeout() {
        assertTimeout(CONNECT_TIMED_OUT, connectTimeoutClient::viaClientConfig);
    }

    @Test
    void givenCorrectEndpoint_whenClientPropertyAndSlowServer_thenReadTimeout() {
        assertTimeout(READ_TIMED_OUT, readTimeoutClient::viaClientProperty);
    }

    @Test
    void givenIncorrectEndpoint_whenClientProperty_thenConnectTimeout() {
        assertTimeout(CONNECT_TIMED_OUT, connectTimeoutClient::viaClientProperty);
    }

    @Test
    void givenCorrectEndpoint_whenRequestPropertyAndSlowServer_thenReadTimeout() {
        assertTimeout(READ_TIMED_OUT, readTimeoutClient::viaRequestProperty);
    }

    @Test
    void givenIncorrectEndpoint_whenRequestProperty_thenConnectTimeout() {
        assertTimeout(CONNECT_TIMED_OUT, connectTimeoutClient::viaRequestProperty);
    }
}