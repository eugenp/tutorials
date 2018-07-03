package com.springwebflux.sample;

import java.time.Duration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Before
    private void setUp() {
        webTestClient =  webTestClient.mutate()
            .responseTimeout(Duration.ofMillis(10000))
            .build();
    }

    @Test
    public void rollDice() throws InterruptedException {
        webTestClient.get()
            .uri("/v1/dice")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(Integer.class);
    }
}