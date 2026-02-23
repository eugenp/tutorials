package com.baeldung.apiversions.queryparam;

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
            .apiVersionInserter(ApiVersionInserter.useQueryParam("version"))
            .build();
    }

    @Test
    void givenProductExists_WhenGetProductIsCalledWithQueryParamVersion1_thenReturnValidProduct() {
        restTestClient.get()
            .uri("/api/products/1001")
            .apiVersion(1)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.name").isEqualTo("apple")
            .jsonPath("$.desc").isEqualTo("apple_desc")
            .jsonPath("$.price").isEqualTo(1.99);
    }

    @Test
    void givenProductExists_WhenGetProductIsCalledWithQueryParamVersion2_thenReturnValidProductV2() {
        restTestClient.get()
            .uri("/api/products/1001")
            .apiVersion(2)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.name").isEqualTo("apple")
            .jsonPath("$.desc").doesNotExist()
            .jsonPath("$.price").isEqualTo(1.99);
    }

    @Test
    void givenProductExists_WhenGetProductIsCalledWithInvalidQueryParam_thenReturnBadRequestError() {
        restTestClient.get()
            .uri("/api/products/1001")
            .apiVersion(3)
            .exchange()
            .expectStatus()
            .is4xxClientError()
            .expectBody()
            .jsonPath("$.name").doesNotExist()
            .jsonPath("$.desc").doesNotExist()
            .jsonPath("$.price").doesNotExist();
    }

    @Test
    void givenProductExists_WhenGetProductIsCalledWithQueryParamVersion1Dot0_thenReturnValidProduct() {
        restTestClient.get()
            .uri("/api/products/1001")
            .apiVersion(1.0)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.name").isEqualTo("apple")
            .jsonPath("$.desc").isEqualTo("apple_desc")
            .jsonPath("$.price").isEqualTo(1.99);
    }
}
