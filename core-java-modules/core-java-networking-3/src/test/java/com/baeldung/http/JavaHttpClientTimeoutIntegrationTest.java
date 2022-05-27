package com.baeldung.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpConnectTimeoutException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.baeldung.http.JavaHttpClientTimeout.getHttpClientWithTimeout;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JavaHttpClientTimeoutIntegrationTest {

    private HttpClient httpClient;
    private HttpRequest httpRequest;

    @BeforeEach
    public void setUp() {
        httpClient = getHttpClientWithTimeout(3);
        httpClient.connectTimeout().map(Duration::toSeconds)
                .ifPresent(sec -> System.out.println("Timeout in seconds: " + sec));

        httpRequest = HttpRequest.newBuilder().uri(URI.create("http://10.255.255.1")).GET().build();
    }

    @Test
    void shouldThrowExceptionWhenMakingSyncCall() {
        HttpConnectTimeoutException thrown = assertThrows(
                HttpConnectTimeoutException.class,
                () -> httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()),
                "Expected doThing() to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("timed out"));
    }

    @Test
    void shouldThrowExceptionWhenMakingASyncCall() throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<String> completableFuture =
                httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .exceptionally(Throwable::getMessage);
        String response = completableFuture.get(5, TimeUnit.SECONDS);
        assertTrue(response.contains("timed out"));
    }
}