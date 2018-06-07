package com.baeldung.reactive.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

class StreamControllerTest {
    private final WebTestClient client = WebTestClient.bindToController(new StreamController())
        .build();

    @Test
    public void notFound() throws Exception {
        this.client.get()
            .uri("/invalid")
            .exchange()
            .expectStatus()
            .isNotFound()
            .expectBody(Void.class);
    }

    @Test
    public void serverException() throws Exception {
        this.client.get()
            .uri("/stream")
            .exchange()
            .expectStatus()
            .isEqualTo(HttpStatus.OK);
    }

}
