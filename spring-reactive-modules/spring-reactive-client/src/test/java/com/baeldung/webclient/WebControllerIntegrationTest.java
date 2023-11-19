package com.baeldung.webclient;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

@DirtiesContext(classMode = BEFORE_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = WebClientApplication.class)
class WebControllerIntegrationTest {

    @LocalServerPort
    private int randomServerPort;

    @Autowired
    private WebTestClient testClient;

    @Autowired
    private WebController webController;

    @BeforeEach
    void setup() {
        webController.setServerPort(randomServerPort);
    }

    @Test
    void whenEndpointWithBlockingClientIsCalled_thenThreeProductsAreReceived() {
        testClient.get()
          .uri("/products-blocking")
          .exchange()
          .expectStatus().isOk()
          .expectBodyList(Product.class).hasSize(3);
    }

    @Test
    void whenEndpointWithNonBlockingClientIsCalled_thenThreeProductsAreReceived() {
        testClient.get()
          .uri("/products-non-blocking")
          .exchange()
          .expectStatus().isOk()
          .expectBodyList(Product.class).hasSize(3);
    }
}