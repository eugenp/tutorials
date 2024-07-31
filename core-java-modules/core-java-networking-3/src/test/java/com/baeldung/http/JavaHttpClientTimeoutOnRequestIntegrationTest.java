package com.baeldung.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpConnectTimeoutException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalTime;

import static com.baeldung.http.JavaHttpClientTimeout.getHttpClientWithTimeout;
import static java.time.LocalTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JavaHttpClientTimeoutOnRequestIntegrationTest {

    private HttpClient httpClient;
    private HttpRequest httpRequest;

    @BeforeEach
    public void setUp() {
        httpClient = getHttpClientWithTimeout(3);
        httpClient.connectTimeout().map(Duration::toSeconds)
                .ifPresent(sec -> System.out.println("Timeout in seconds: " + sec));

        httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://10.255.255.1"))
                .timeout(Duration.ofSeconds(1))
                .GET()
                .build();
    }

    @Test
    void shouldThrowExceptionWhithin1SecondWhenMakingSyncCall() {
        LocalTime beforeHttpCall = now();
        HttpConnectTimeoutException thrown = assertThrows(
                HttpConnectTimeoutException.class,
                () -> httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()),
                "Expected doThing() to throw, but it didn't"
        );
        LocalTime afterHttpCall = now();
        assertTrue(thrown.getMessage().contains("timed out"));
        assertEquals(1, Duration.between(beforeHttpCall, afterHttpCall).getSeconds());
    }

}
