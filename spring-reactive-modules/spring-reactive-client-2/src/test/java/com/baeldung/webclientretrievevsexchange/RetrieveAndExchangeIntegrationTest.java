package com.baeldung.webclientretrievevsexchange;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest
class RetrieveAndExchangeIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void givenFirstUser_whenRetrieveMethodIsUsed_thenReturnOk() throws Exception {
        this.webTestClient.get()
            .uri("/user/1")
            .exchange()
            .expectStatus()
            .isOk();
    }

    @Test
    void givenFirstUser_whenRetreiveMethodIsUsedWithOnStatusHandler_thenReturnNotFound() throws Exception {
        this.webTestClient.get()
            .uri("/user-status/100")
            .exchange()
            .expectStatus()
            .is5xxServerError();
    }

    @Test
    void givenFirstUser_whenExchangeMonoMethodIsUsed_thenReturnOk() throws Exception {
        this.webTestClient.get()
            .uri("/user/exchange-mono/1")
            .exchange()
            .expectStatus()
            .isOk();
    }

    @Test
    void givenAllUsers_whenRetrieveMethodIsUsed_thenReturnOk() throws Exception {
        this.webTestClient.get()
            .uri("/users")
            .exchange()
            .expectStatus()
            .isOk();
    }

    @Test
    void givenSingleUser_whenResponseBodyIsAltered_thenReturnOk() throws Exception {
        this.webTestClient.get()
            .uri("/user/exchange-alter/1")
            .exchange()
            .expectBody()
            .json("{\"id\":101,\"name\":\"LEANNE GRAHAM\"}");
    }

    @Test
    void givenAllUsers_whenExchangeFluxMethodIsUsed_thenReturnOk() throws Exception {
        this.webTestClient.get()
            .uri("/user-exchange-flux")
            .exchange()
            .expectStatus()
            .isOk();
    }

}