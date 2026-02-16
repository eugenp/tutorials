package com.baeldung.apiversions.queryparam;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerLiveTest {

    private RestTestClient restTestClient;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp(WebApplicationContext context) {
        restTestClient = RestTestClient
            .bindToServer()
            .baseUrl("http://localhost:" + port)
            .build();
    }

    @Test
    void givenProductExists_WhenGetProductIsCalled_WithQueryParamVersion1_thenReturnValidProduct() {
        restTestClient.get()
            .uri("/api/products/1001?version=1")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.name").isEqualTo("apple")
            .jsonPath("$.desc").isEqualTo("apple_desc")
            .jsonPath("$.price").isEqualTo(1.99);
    }

    @Test
    void givenProductExists_WhenGetProductIsCalled_WithQueryParamVersion2_thenReturnValidProductV2() {
        restTestClient.get()
            .uri("/api/products/1001?version=2")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.name").isEqualTo("apple")
            .jsonPath("$.desc").doesNotExist()
            .jsonPath("$.price").isEqualTo(1.99);
    }

    @Test
    void givenProductExists_WhenGetProductIsCalled_WithInvalidQueryParam_thenReturnBadRequestError() {
        restTestClient.get()
            .uri("/api/products/1001?version=invalid")
            .exchange()
            .expectStatus()
            .is4xxClientError()
            .expectBody()
            .jsonPath("$.name").doesNotExist()
            .jsonPath("$.desc").doesNotExist()
            .jsonPath("$.price").doesNotExist();
    }
}
