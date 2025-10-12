package com.baeldung.spring.resttestclient;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

public class RestTestClientTest {

    RestTestClient client = RestTestClient.bindToServer()
        .baseUrl("http://localhost:8080")
        .build();

    @Test
    void should() {
        System.out.println("Foo");

        client.get()
            .uri("/persons/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON);
    }
}
