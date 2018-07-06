package com.baeldung.reactive.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest
public class FibonacciControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void fibonacciShouldGenerateOneValPerSecond() {
        FluxExchangeResult<Long> result = this.webTestClient.get().uri("/fibonacci")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Long.class);

        Flux fibonacciFlux = result.getResponseBody();

        StepVerifier.create(fibonacciFlux)
                .expectSubscription()
                .expectNext(1L)
                .thenAwait(Duration.ofSeconds(1))
                .expectNext(1L)
                .thenAwait(Duration.ofSeconds(1))
                .expectNext(2L)
                .thenAwait(Duration.ofSeconds(1))
                .expectNext(3L)
                .thenAwait(Duration.ofSeconds(1))
                .expectNext(5L)
                .thenCancel()
                .verify();
    }
}
