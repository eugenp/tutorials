package com.baeldung.concurrent.completablefuture;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private static final String DEFAULT_PRODUCT = "default_product";
    private static final String PRODUCT_OFFERS = "product_offers";

    @BeforeAll
    void setUp() {
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
        WireMock.configureFor("localhost", 8080);

        stubFor(get(urlEqualTo("/api/dummy"))
                .willReturn(aResponse()
                        .withFixedDelay(5000) // must be > DEFAULT_TIMEOUT for a timeout to occur.
                        .withBody(PRODUCT_OFFERS)));

        executorService = Executors.newScheduledThreadPool(1);
    }


    @AfterAll
    void tearDown() {
        executorService.shutdown();
        wireMockServer.stop();
    }

    private CompletableFuture<String> fetchProductData() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                URL url = new URL("http://localhost:8080/api/dummy");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    return response.toString();
                } finally {
                    connection.disconnect();
                }
            } catch (IOException e) {
                return "";
            }
        });
    }

    @Test
    void whenorTimeout_thenGetThrow() {
        CompletableFuture<String> completableFuture = fetchProductData();
        completableFuture.orTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        assertThrows(ExecutionException.class, completableFuture::get);
    }

    @Test
    void whencompleteOnTimeout_thenReturnValue() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = fetchProductData();
        completableFuture.completeOnTimeout(DEFAULT_PRODUCT, DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        assertEquals(DEFAULT_PRODUCT, completableFuture.get());
    }

    @Test
    void whencompleteExceptionally_thenGetThrow() {
        CompletableFuture<String> completableFuture = fetchProductData();
        executorService.schedule(() -> completableFuture
                .completeExceptionally(new TimeoutException("Timeout occurred")), DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        assertThrows(ExecutionException.class, completableFuture::get);
    }
}
