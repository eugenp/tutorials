package com.baeldung.concurrent.completablefuture;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import net.minidev.json.writer.JsonReader;
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
import java.util.function.Supplier;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CompletableFutureTimeoutUnitTest {
    private WireMockServer wireMockServer;
    private ScheduledExecutorService executorService;
    private static final int DEFAULT_TIMEOUT = 1000; //1 seconds
    private static final String DEFAULT_PRODUCT = "{\n" +
            "  \"products\": [\n" +
            "    {\n" +
            "      \"id\": 2,\n" +
            "      \"title\": \"iPhone X\",\n" +
            "      \"description\": \"SIM-Free, Model A19211 6.5-inch Super Retina HD display with OLED technology A12 Bionic chip with ...\",\n" +
            "      \"price\": 899,\n" +
            "      \"discountPercentage\": 0.0,\n" +
            "      \"rating\": 4.44,\n" +
            "      \"stock\": 34,\n" +
            "      \"brand\": \"Apple\",\n" +
            "      \"category\": \"smartphones\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 3,\n" +
            "      \"title\": \"Samsung Universe 9\",\n" +
            "      \"description\": \"Samsung's new variant which goes beyond Galaxy to the Universe\",\n" +
            "      \"price\": 1249,\n" +
            "      \"discountPercentage\": 0.0,\n" +
            "      \"rating\": 4.09,\n" +
            "      \"stock\": 36,\n" +
            "      \"brand\": \"Samsung\",\n" +
            "      \"category\": \"smartphones\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"total\": 2\n" +
            "}";
    private static final String PRODUCT_OFFERS = "{\n" +
            "  \"products\": [\n" +
            "    {\n" +
            "      \"id\": 1,\n" +
            "      \"title\": \"iPhone 9\",\n" +
            "      \"description\": \"An apple mobile which is nothing like apple\",\n" +
            "      \"price\": 549,\n" +
            "      \"discountPercentage\": 12.96,\n" +
            "      \"rating\": 4.69,\n" +
            "      \"stock\": 94,\n" +
            "      \"brand\": \"Apple\",\n" +
            "      \"category\": \"smartphones\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 2,\n" +
            "      \"title\": \"iPhone X\",\n" +
            "      \"description\": \"SIM-Free, Model A19211 6.5-inch Super Retina HD display with OLED technology A12 Bionic chip with ...\",\n" +
            "      \"price\": 899,\n" +
            "      \"discountPercentage\": 17.94,\n" +
            "      \"rating\": 4.44,\n" +
            "      \"stock\": 34,\n" +
            "      \"brand\": \"Apple\",\n" +
            "      \"category\": \"smartphones\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 3,\n" +
            "      \"title\": \"Samsung Universe 9\",\n" +
            "      \"description\": \"Samsung's new variant which goes beyond Galaxy to the Universe\",\n" +
            "      \"price\": 1249,\n" +
            "      \"discountPercentage\": 15.46,\n" +
            "      \"rating\": 4.09,\n" +
            "      \"stock\": 36,\n" +
            "      \"brand\": \"Samsung\",\n" +
            "      \"category\": \"smartphones\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 4,\n" +
            "      \"title\": \"OPPOF19\",\n" +
            "      \"description\": \"OPPO F19 is officially announced on April 2021.\",\n" +
            "      \"price\": 280,\n" +
            "      \"discountPercentage\": 17.91,\n" +
            "      \"rating\": 4.3,\n" +
            "      \"stock\": 123,\n" +
            "      \"brand\": \"OPPO\",\n" +
            "      \"category\": \"smartphones\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 5,\n" +
            "      \"title\": \"Huawei P30\",\n" +
            "      \"description\": \"Huawei’s re-badged P30 Pro New Edition was officially unveiled yesterday in Germany and now the device has made its way to the UK.\",\n" +
            "      \"price\": 499,\n" +
            "      \"discountPercentage\": 10.58,\n" +
            "      \"rating\": 4.09,\n" +
            "      \"stock\": 32,\n" +
            "      \"brand\": \"Huawei\",\n" +
            "      \"category\": \"smartphones\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"total\": 5\n" +
            "}";

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
