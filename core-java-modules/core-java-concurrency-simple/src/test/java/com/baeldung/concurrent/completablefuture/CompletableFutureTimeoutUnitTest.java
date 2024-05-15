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
    private static final String DEFAULT_PRODUCT = """
            {
              "products": [
                {
                  "id": 2,
                  "title": "iPhone X",
                  "description": "SIM-Free, Model A19211 6.5-inch Super Retina HD display with OLED technology A12 Bionic chip with ...",
                  "price": 899,
                  "discountPercentage": 0.0,
                  "rating": 4.44,
                  "stock": 34,
                  "brand": "Apple",
                  "category": "smartphones"
                },
                {
                  "id": 3,
                  "title": "Samsung Universe 9",
                  "description": "Samsung's new variant which goes beyond Galaxy to the Universe",
                  "price": 1249,
                  "discountPercentage": 0.0,
                  "rating": 4.09,
                  "stock": 36,
                  "brand": "Samsung",
                  "category": "smartphones"
                }
              ],
              "total": 2
            }""";
    private static final String PRODUCT_OFFERS = """
            {
              "products": [
                {
                  "id": 1,
                  "title": "iPhone 9",
                  "description": "An apple mobile which is nothing like apple",
                  "price": 549,
                  "discountPercentage": 12.96,
                  "rating": 4.69,
                  "stock": 94,
                  "brand": "Apple",
                  "category": "smartphones"
                },
                {
                  "id": 2,
                  "title": "iPhone X",
                  "description": "SIM-Free, Model A19211 6.5-inch Super Retina HD display with OLED technology A12 Bionic chip with ...",
                  "price": 899,
                  "discountPercentage": 17.94,
                  "rating": 4.44,
                  "stock": 34,
                  "brand": "Apple",
                  "category": "smartphones"
                },
                {
                  "id": 3,
                  "title": "Samsung Universe 9",
                  "description": "Samsung's new variant which goes beyond Galaxy to the Universe",
                  "price": 1249,
                  "discountPercentage": 15.46,
                  "rating": 4.09,
                  "stock": 36,
                  "brand": "Samsung",
                  "category": "smartphones"
                },
                {
                  "id": 4,
                  "title": "OPPOF19",
                  "description": "OPPO F19 is officially announced on April 2021.",
                  "price": 280,
                  "discountPercentage": 17.91,
                  "rating": 4.3,
                  "stock": 123,
                  "brand": "OPPO",
                  "category": "smartphones"
                },
                {
                  "id": 5,
                  "title": "Huawei P30",
                  "description": "Huawei’s re-badged P30 Pro New Edition was officially unveiled yesterday in Germany and now the device has made its way to the UK.",
                  "price": 499,
                  "discountPercentage": 10.58,
                  "rating": 4.09,
                  "stock": 32,
                  "brand": "Huawei",
                  "category": "smartphones"
                }
              ],
              "total": 5
            }""";

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

                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

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
        CompletableFuture<String> productDataFuture = fetchProductData();
        productDataFuture.orTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        assertThrows(ExecutionException.class, productDataFuture::get);
    }

    @Test
    void whencompleteOnTimeout_thenReturnValue() throws ExecutionException, InterruptedException {
        CompletableFuture<String> productDataFuture = fetchProductData();
        productDataFuture.completeOnTimeout(DEFAULT_PRODUCT, DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        assertEquals(DEFAULT_PRODUCT, productDataFuture.get());
    }

    @Test
    void whencompleteExceptionally_thenGetThrow() {
        CompletableFuture<String> productDataFuture = fetchProductData();
        executorService.schedule(() -> productDataFuture
                .completeExceptionally(new TimeoutException("Timeout occurred")), DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        assertThrows(ExecutionException.class, productDataFuture::get);
    }
}
