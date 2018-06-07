package com.baeldung.reactive.controller;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class InfiniteFluxControllerIntegrationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(InfiniteFluxControllerIntegrationTests.class);

    private WebTestClient webTestClient;

    @Before
    public void setUp() {
        this.webTestClient = WebTestClient.bindToController(new InfiniteFluxController())
            .build();
    }

    @Test
    public void whenPositiveNumberCountIsGiven_thenFetchAndLogTillCount() {

        int countTillNumber = 5;

        Flux<Long> longFlux1 = this.webTestClient.get()
            .uri("/numbers/" + countTillNumber)
            .accept(MediaType.APPLICATION_STREAM_JSON)
            .exchange()
            .returnResult(Long.class)
            .getResponseBody();

        StepVerifier.create(longFlux1)
            .expectNext(0L, 1L, 2L, 3L, 4L, 5L)
            .expectComplete()
            .verify();
    }
}
