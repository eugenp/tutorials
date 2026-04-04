package com.baeldung.apiversions.pathsegment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.client.ApiVersionInserter;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerLiveTest {

    private RestTestClient restTestClient;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTestClient = RestTestClient
            .bindToServer()
            .baseUrl("http://localhost:" + port)
            .apiVersionInserter(ApiVersionInserter.usePathSegment(1))
            .build();
    }

    @Test
    void givenProductExists_whenGetProductIsCalledWithPathSegmentV1_thenReturnValidProduct() {
        restTestClient.get()
            .uri("/api/products/1001")
            .apiVersion("v1")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.name").isEqualTo("apple")
            .jsonPath("$.desc").isEqualTo("apple_desc")
            .jsonPath("$.price").isEqualTo(1.99);
    }

    @Test
    void givenProductExists_whenGetProductIsCalledWithPathSegmentV2_thenReturnValidProduct() {
        restTestClient.get()
            .uri("/api/products/1001")
            .apiVersion("v2")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.name").isEqualTo("apple")
            .jsonPath("$.desc").doesNotExist()
            .jsonPath("$.price").isEqualTo(1.99);
    }

    @Test
    void givenProductExists_whenGetProductIsCalledWithPathSegment2_thenThrowNotFoundError() {
        restTestClient.get()
            .uri("/api/3/products/1001")
            .apiVersion(3)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void givenProductExists_whenGetProductIsCalledWithPathSegmentV1Dot0_thenReturnValidProduct() {
        restTestClient.get()
            .uri("/api/products/1001")
            .apiVersion("v1.0")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.name").isEqualTo("apple")
            .jsonPath("$.desc").isEqualTo("apple_desc")
            .jsonPath("$.price").isEqualTo(1.99);
    }

}
