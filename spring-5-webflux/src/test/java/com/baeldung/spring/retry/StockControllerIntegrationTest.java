package com.baeldung.spring.retry;

import static org.mockito.BDDMockito.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@WebFluxTest
public class StockControllerIntegrationTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private ExternalConnector externalConnector;

    @Test
    public void shouldReturnStockData() {
        given(externalConnector.getData("ABC")).willReturn(Mono.just("stock data"));

        webClient.get()
            .uri("/stocks/data/{id}", "ABC")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(String.class)
            .isEqualTo("stock data");
    }
}
