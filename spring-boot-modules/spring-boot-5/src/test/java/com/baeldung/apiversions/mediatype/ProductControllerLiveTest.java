package com.baeldung.apiversions.mediatype;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

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
            .build();
    }

    @Test
    void givenProductExists_WhenGetProductIsCalled_WithValidMediaTypeVersion_thenReturnValidProduct() {
        restTestClient.get()
            .uri("/api/products/1001")
            .accept(MediaType.valueOf("application/vnd.baeldung.product+json;version=1"))
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType("application/vnd.baeldung.product+json;version=1")
            .expectBody()
            .jsonPath("$.name").isEqualTo("apple")
            .jsonPath("$.desc").isEqualTo("apple_desc")
            .jsonPath("$.price").isEqualTo(1.99);
    }

    @Test
    void givenProductExists_WhenGetProductIsCalled_WithValidMediaType_thenReturnValidProduct() {
        restTestClient.get()
            .uri("/api/products/1001")
            .accept(MediaType.valueOf("application/vnd.baeldung.product+json;version=2"))
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType("application/vnd.baeldung.product+json;version=2")
            .expectBody()
            .jsonPath("$.name").isEqualTo("apple")
            .jsonPath("$.desc").doesNotExist()
            .jsonPath("$.price").isEqualTo(1.99);
    }

    @Test
    void givenProductExists_WhenGetProductIsCalled_WithInValidMediaTypeVersion_thenReturnBadRequestError() {
        restTestClient.get()
            .uri("/api/products/1001")
            .accept(MediaType.valueOf("application/vnd.baeldung.product+json;version=3"))
            .exchange()
            .expectStatus()
            .is4xxClientError()
            .expectBody()
            .jsonPath("$.name").doesNotExist()
            .jsonPath("$.desc").doesNotExist()
            .jsonPath("$.price").doesNotExist();
    }

    @Test
    void givenProductExists_WhenGetProductIsCalled_WithInValidMediaType_thenReturnBadRequestError() {
        restTestClient.get()
            .uri("/api/products/1001")
            .accept(MediaType.valueOf("application/invalid"))
            .exchange()
            .expectStatus()
            .is4xxClientError()
            .expectBody()
            .jsonPath("$.name").doesNotExist()
            .jsonPath("$.desc").doesNotExist()
            .jsonPath("$.price").doesNotExist();
    }

    @Test
    void givenProductExists_WhenGetProductIsCalled_WithValidMediaTypeVersion1_0_thenReturnValidProduct() {
        restTestClient.get()
            .uri("/api/products/1001")
            .accept(MediaType.valueOf("application/vnd.baeldung.product+json;version=1.0"))
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType("application/vnd.baeldung.product+json;version=1.0")
            .expectBody()
            .jsonPath("$.name").isEqualTo("apple")
            .jsonPath("$.desc").isEqualTo("apple_desc")
            .jsonPath("$.price").isEqualTo(1.99);
    }
}
