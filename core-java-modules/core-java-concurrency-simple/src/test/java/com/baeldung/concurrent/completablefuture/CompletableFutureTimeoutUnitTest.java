package com.baeldung.concurrent.completablefuture;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
public class CompletableFutureTimeoutUnitTest {
    private static WireMockServer wireMockServer;
    private static ScheduledExecutorService executorService;
    private static final int DEFAULT_TIMEOUT = 500; //0.5 seconds
    private static final int TIMEOUT_STATUS_CODE = 408; //0.5 seconds

    @BeforeAll
    public static void setUp() {
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
        WireMock.configureFor("localhost", 8080);

        stubFor(get(urlEqualTo("/api/dummy"))
                .willReturn(aResponse()
                        .withFixedDelay(5000) // must be > DEFAULT_TIMEOUT for a timeout to occur.
                        .withStatus(408)));

        executorService = Executors.newScheduledThreadPool(1);
    }


    @AfterAll
    public static void tearDown() {
        executorService.shutdown();
        wireMockServer.stop();
    }

    private CompletableFuture<Integer> createDummyRequest() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                URL url = new URL("http://localhost:8080/api/dummy");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                try {
                    return connection.getResponseCode();
                } finally {
                    connection.disconnect();
                }
            } catch (IOException e) {
                return TIMEOUT_STATUS_CODE;
            }
        });
    }

    @Test
    public void whenorTimeout_thenGetThrow() {
        CompletableFuture<Integer> completableFuture = createDummyRequest()
                .orTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        assertThrows(ExecutionException.class, completableFuture::get);
    }

    @Test
    public void whencompleteOnTimeout_thenReturnValue() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> completableFuture = createDummyRequest()
                .completeOnTimeout(TIMEOUT_STATUS_CODE, DEFAULT_TIMEOUT,
                        TimeUnit.MILLISECONDS);
        assertEquals(TIMEOUT_STATUS_CODE, completableFuture.get());
    }

    @Test
    public void whencompleteExceptionally_thenGetThrow() {
        CompletableFuture<Integer> completableFuture = createDummyRequest();
        executorService.schedule(() -> completableFuture
                .completeExceptionally(new TimeoutException("Timeout occurred")), DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        assertThrows(ExecutionException.class, completableFuture::get);
    }

}
