package com.baeldung.reactive.webflux.client;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PriceControllerUnitTest {

    @Autowired
    private WebTestClient testClient;

    @Test
    public void givenStockName_whenGetCurrentPriceByStock_thenCorrectStockPrice() {

        testClient.get()
                .uri("/api/stock/currentPrice?stockName=Flipkart")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Flux.class);

    }
}
