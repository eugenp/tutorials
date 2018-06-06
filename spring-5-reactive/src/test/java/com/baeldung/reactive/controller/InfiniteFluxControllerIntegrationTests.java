package com.baeldung.reactive.controller;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InfiniteFluxControllerIntegrationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(InfiniteFluxControllerIntegrationTests.class);

    private static WebClient client;
    private static String SERVER_PORT = "8080";

    @BeforeClass
    public static void setup() throws Exception {
        client = WebClient.builder()
            .baseUrl("http://localhost:" + SERVER_PORT)
            .build();
    }

    @Test
    public void whenPositiveNumberCountIsGiven_thenFetchAndLogTillCount() {

        int countTillNumber = 5;

        Flux<Long> longFlux = client.get()
            .uri("/numbers/" + countTillNumber)
            .accept(MediaType.APPLICATION_STREAM_JSON)
            .retrieve()
            .bodyToFlux(Long.class);

        Duration verificationTime = StepVerifier.create(longFlux)
            .expectNext(0L, 1L, 2L, 3L, 4L, 5L)
            .expectComplete()
            .verify();
        LOGGER.info("Verified successfully in {} seconds", verificationTime.get(ChronoUnit.SECONDS));

    }
}
