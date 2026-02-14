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
@ContextConfiguration(classes = { ProductControllerWithCustomMedia.class, WebMediaTypeConfig.class })
class ProductControllerMediaTypeLiveTest {

    private RestTestClient restTestClient;

    @BeforeEach
    void setUp(WebApplicationContext context) {
        restTestClient = RestTestClient.bindToApplicationContext(context)
            .build();
    }

    @Test
    void givenProductExists_WhenProductAPIIsCalled_WithValidMediaTypeVersion_thenReturnValidProduct() {
        restTestClient.get()
            .uri("/api/products/1001")
            .accept(MediaType.valueOf("application/vnd.baeldung.product+json;version=1"))
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
    void givenProductExists_WhenProductAPIIsCalled_WithValidMediaType_thenReturnValidProduct() {
        restTestClient.get()
            .uri("/api/products/1001")
            .accept(MediaType.valueOf("application/vnd.baeldung.product+json;version=2"))
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
    void givenProductExists_WhenProductAPIIsCalled_WithInValidMediaTypeVersion_thenReturnBadRequestError() {
        restTestClient.get()
            .uri("/api/products/1001")
            .accept(MediaType.valueOf("application/vnd.baeldung.product+json;version=3"))
            .exchange()
            .expectStatus()
            .is4xxClientError()
            .expectBody()
            .jsonPath("$.name")
            .doesNotExist()
            .jsonPath("$.desc")
            .doesNotExist()
            .jsonPath("$.price")
            .doesNotExist();
    }

    @Test
    void givenProductExists_WhenProductAPIIsCalled_WithInValidMediaType_thenReturnBadRequestError() {
        restTestClient.get()
            .uri("/api/products/1001")
            .accept(MediaType.valueOf("application/invalid"))
            .exchange()
            .expectStatus()
            .is4xxClientError()
            .expectBody()
            .jsonPath("$.name")
            .doesNotExist()
            .jsonPath("$.desc")
            .doesNotExist()
            .jsonPath("$.price")
            .doesNotExist();
    }
}
