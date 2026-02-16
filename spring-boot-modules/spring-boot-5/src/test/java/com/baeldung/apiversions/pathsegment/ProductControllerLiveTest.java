package com.baeldung.apiversions.pathsegment;

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
    void givenProductExists_WhenGetProductIsCalled_WithPathSegmentV1_thenReturnValidProduct() {
        restTestClient.get()
            .uri("/api/v1/products/1001")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.name").isEqualTo("apple")
            .jsonPath("$.desc").isEqualTo("apple_desc")
            .jsonPath("$.price").isEqualTo(1.99);
    }

    @Test
    void givenProductExists_WhenGetProductIsCalled_WithPathSegmentV2_thenReturnValidProduct() {
        restTestClient.get()
            .uri("/api/v2/products/1001")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.name").isEqualTo("apple")
            .jsonPath("$.desc").doesNotExist()
            .jsonPath("$.price").isEqualTo(1.99);
    }

    @Test
    void givenProductExists_WhenGetProductIsCalled_WithPathSegment2_thenThrowNotFoundError() {
        restTestClient.get()
            .uri("/api/2/products/1001")
            .exchange()
            .expectStatus()
            .isNotFound();
    }

}
