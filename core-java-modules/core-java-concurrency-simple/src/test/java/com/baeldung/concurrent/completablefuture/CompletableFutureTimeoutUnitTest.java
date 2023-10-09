package com.baeldung.concurrent.completablefuture;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CompletableFutureTimeoutUnitTest {
    private WireMockServer wireMockServer;
    private ScheduledExecutorService executorService;
    private static final int DEFAULT_TIMEOUT = 1000; //1 seconds
    private static final int TIMEOUT_STATUS_CODE = 408; //0.5 seconds

    @BeforeAll
    void setUp() {
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
    void tearDown() {
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
            } catch (Exception e) {
                return TIMEOUT_STATUS_CODE;
            }
        });
    }

    @Test
    void whenorTimeout_thenGetThrow() {
        CompletableFuture<Integer> completableFuture = createDummyRequest();
        completableFuture.orTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        assertThrows(ExecutionException.class, completableFuture::get);
    }

    @Test
    void whencompleteOnTimeout_thenReturnValue() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> completableFuture = createDummyRequest();
        completableFuture.completeOnTimeout(TIMEOUT_STATUS_CODE, DEFAULT_TIMEOUT,TimeUnit.MILLISECONDS);
        assertEquals(TIMEOUT_STATUS_CODE, completableFuture.get());
    }

    @Test
    void whencompleteExceptionally_thenGetThrow() {
        CompletableFuture<Integer> completableFuture = createDummyRequest();
        executorService.schedule(() -> completableFuture
                .completeExceptionally(new TimeoutException("Timeout occurred")), DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        assertThrows(ExecutionException.class, completableFuture::get);
    }
}
