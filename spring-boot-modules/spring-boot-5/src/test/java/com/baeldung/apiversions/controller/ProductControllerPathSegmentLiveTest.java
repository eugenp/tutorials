package com.baeldung.apiversions.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.context.WebApplicationContext;

import com.baeldung.apiversions.ExampleApplication;
import com.baeldung.apiversions.config.WebHeaderBasedConfig;
import com.baeldung.apiversions.config.WebMediaTypeConfig;
import com.baeldung.apiversions.config.WebPathSegmentConfig;
import com.baeldung.apiversions.config.WebQueryParamConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ExampleApplication.class)
@ContextConfiguration(classes = { ProductControllerWithPathSegment.class, WebPathSegmentConfig.class })
@DisplayName("All Get Products API Versions Tests")
class ProductControllerPathSegmentLiveTest {

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
            .jsonPath("$.name")
            .isEqualTo("apple")
            .jsonPath("$.desc")
            .isEqualTo("apple_long_desc")
            .jsonPath("$.price")
            .isEqualTo(1.99);
    }

    @Test
    void givenProductExists_WhenProductAPIIsCalled_WithPathSegmentV2_thenReturnValidProduct() {
        restTestClient.get()
            .uri("/api/v2/products/1001")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.name")
            .isEqualTo("apple")
            .jsonPath("$.desc")
            .doesNotExist()
            .jsonPath("$.price")
            .isEqualTo(1.99);
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
