package com.baeldung.webflux.reactiveapp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReactiveServerApplicationUnitTest {
    private WebTestClient webTestClient;

    @Before
    public void setUp() {
        webTestClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:8080")
                .build();
    }

    @Test
    public void givenRestApi_whenIncorrectEndpointIsInvoked_ShouldGetStatus404() {
        webTestClient
                .get()
                .uri("/invalidPath")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Test
    public void givenRestApi_whenCorrectEndpointIsInvoked_ShouldGetStatus200() {
        webTestClient
                .get()
                .uri("/emitEvents")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();
    }
}