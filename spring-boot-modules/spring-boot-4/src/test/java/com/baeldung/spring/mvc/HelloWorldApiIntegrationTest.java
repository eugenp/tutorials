package com.baeldung.spring.mvc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HelloWorldApiIntegrationTest {

    RestTestClient client;

    @BeforeEach
    void setUp(WebApplicationContext context) {
        client = RestTestClient.bindToApplicationContext(context)
            .build();
    }

    @Test
    void shouldFetchHelloV1() {
        client.get()
            .uri("/api/v1/hello")
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.TEXT_PLAIN)
            .expectBody(String.class)
            .consumeWith(message -> assertThat(message.getResponseBody()).containsIgnoringCase("hello"));
    }

    @Test
    void shouldFetchHelloV2() {
        client.get()
            .uri("/api/v2/hello")
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.TEXT_PLAIN)
            .expectBody(String.class)
            .consumeWith(message -> assertThat(message.getResponseBody()).containsIgnoringCase("hi"));
    }

    @Test
    void shouldFetchHelloV3() {
        client.get()
            .uri("/api/v3/hello")
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.TEXT_PLAIN)
            .expectBody(String.class)
            .consumeWith(message -> assertThat(message.getResponseBody()).containsIgnoringCase("hey"));
    }

}
