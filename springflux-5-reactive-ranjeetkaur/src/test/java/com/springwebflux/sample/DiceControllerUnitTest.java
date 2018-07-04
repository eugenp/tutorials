package com.springwebflux.sample;

import java.time.Duration;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiceControllerUnitTest{

    @Autowired
    private WebTestClient webTestClient;

    @Before
    public void setUp() {
        webTestClient =  webTestClient.mutate()
            .responseTimeout(Duration.ofMillis(10000))
            .build();
    }

    @Test
    public void testRollDice() throws InterruptedException {
        webTestClient.get()
            .uri("/v1/dice")
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
            .expectBodyList(Integer.class)
            .consumeWith(response -> System.out.println("C"+response));
    }
}