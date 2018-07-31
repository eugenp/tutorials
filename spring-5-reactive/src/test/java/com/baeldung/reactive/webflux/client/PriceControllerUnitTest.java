package com.baeldung.reactive.webflux.client;

import com.baeldung.reactive.webflux.server.controller.PriceController;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PriceControllerUnitTest {

    private final WebTestClient testClient = WebTestClient.bindToController(new PriceController())
            .configureClient()
            .build();
    @Test
    public void givenStockName_whenGetCurrentPriceByStock_thenCorrectStockPrice() {
        testClient.get()
                .uri("/api/stock/currentPrice?stockName=Flipkart")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Double.class);
    }
}