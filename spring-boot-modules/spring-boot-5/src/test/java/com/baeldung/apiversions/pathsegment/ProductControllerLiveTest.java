package com.baeldung.apiversions.pathsegment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ExampleApplication.class)
class ProductControllerLiveTest {

    private RestTestClient restTestClient;

    @BeforeEach
    void setUp(WebApplicationContext context) {
        restTestClient = RestTestClient.bindToApplicationContext(context)
            .build();
    }

    @Test
    void givenProductExists_WhenGetProductAPIIsCalled_WithPathSegmentV1_thenReturnValidProduct() {
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
    void givenProductExists_WhenProductAPIIsCalled_WithPathSegmentV2_thenReturnValidProduct() {
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
    void givenProductExists_WhenProductAPIIsCalled_WithPathSegment2_thenThrowNotFoundError() {
        restTestClient.get()
            .uri("/api/2/products/1001")
            .exchange()
            .expectStatus()
            .isNotFound();
    }

}
