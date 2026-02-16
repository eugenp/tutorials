package com.baeldung.apiversions.header;

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
    void givenProductExists_WhenGetProductIsCalled_WithHeaderVersion1_thenReturnValidProduct() {
        restTestClient.get()
            .uri("/api/products/1001")
            .header("X-API-Version", "1")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.name").isEqualTo("apple")
            .jsonPath("$.desc").isEqualTo("apple_desc")
            .jsonPath("$.price").isEqualTo(1.99);
    }

    @Test
    void givenProductExists_WhenGetProductIsCalled_WithHeaderVersion2_thenReturnValidProduct() {
        restTestClient.get()
            .uri("/api/products/1001")
            .header("X-API-Version", "2")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.name").isEqualTo("apple")
            .jsonPath("$.desc").doesNotExist()
            .jsonPath("$.price").isEqualTo(1.99);
    }

    @Test
    void givenProductExists_WhenGetProductIsCalled_WithInvalidHeaderVersion_thenReturnBadRequestError() {
        restTestClient.get()
            .uri("/api/products/1001")
            .header("X-API-Version", "3")
            .exchange()
            .expectStatus()
            .is4xxClientError()
            .expectBody()
            .jsonPath("$.name").doesNotExist()
            .jsonPath("$.desc").doesNotExist()
            .jsonPath("$.price").doesNotExist();
    }
}
