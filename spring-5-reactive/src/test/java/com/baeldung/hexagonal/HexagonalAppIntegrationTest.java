package com.baeldung.hexagonal;

import com.baeldung.hexagonal.core.model.ExchangeRate;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HexagonalApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class HexagonalAppIntegrationTest {

    private static WebTestClient client;

    @BeforeClass
    public static void setup() throws Exception {
        client = WebTestClient.bindToServer()
                .baseUrl("http://localhost:8080")
                .build();
    }

    @Test
    public void givenRouter_whenPostRates_thenGotAccepted() throws Exception {
        client.post()
                .uri("/rates")
                .exchange()
                .expectStatus()
                .isAccepted();
    }

    @Test
    public void givenRouter_whenGetRates_thenGotResponse() throws Exception {
        client.post()
                .uri("/rates")
                .exchange()
                .expectStatus()
                .isAccepted();

        client.get()
                .uri("/rates")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(ExchangeRate.class)
                .hasSize(10);
    }
}
