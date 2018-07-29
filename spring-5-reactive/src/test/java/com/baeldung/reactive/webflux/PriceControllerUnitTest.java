package com.baeldung.reactive.webflux;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

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
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(Double.class);
    }
}
