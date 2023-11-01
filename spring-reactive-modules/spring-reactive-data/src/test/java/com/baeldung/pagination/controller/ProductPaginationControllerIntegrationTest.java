package com.baeldung.pagination.controller;

import static org.assertj.core.api.Assertions.atIndex;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.pagination.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ProductPaginationControllerIntegrationTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    void WhenProductEndpointIsHit_thenShouldReturnProductsWithPagination() throws JsonProcessingException {
        String response = webClient.get()
            .uri("/products")
            .exchange()
            .expectStatus()
            .is2xxSuccessful()
            .expectBody(String.class)
            .returnResult()
            .getResponseBody();
        Assertions.assertNotNull(response);

        JsonNode pageResponse = new ObjectMapper().readValue(response, JsonNode.class);
        Assertions.assertNotNull(pageResponse);
        Assertions.assertEquals(4, pageResponse.get("totalElements")
            .asInt());
        Assertions.assertEquals(1, pageResponse.get("totalPages")
            .asInt());
        Assertions.assertTrue(pageResponse.get("last")
            .asBoolean());
        Assertions.assertTrue(pageResponse.get("first")
            .asBoolean());
        Assertions.assertEquals(100, pageResponse.get("size")
            .asInt());
        Assertions.assertEquals(4, pageResponse.get("numberOfElements")
            .asInt());
        Assertions.assertEquals(0, pageResponse.get("pageable")
            .get("offset")
            .asInt());
        Assertions.assertEquals(0, pageResponse.get("pageable")
            .get("pageNumber")
            .asInt());
        Assertions.assertEquals(100, pageResponse.get("pageable")
            .get("pageSize")
            .asInt());
        Assertions.assertTrue(pageResponse.get("pageable")
            .get("paged")
            .asBoolean());
        List<Product> content = new ObjectMapper().readValue(String.valueOf(pageResponse.get("content")), new TypeReference<List<Product>>() {
        });
        assertThat(content).hasSize(4);
        assertThat(content).extracting("name", "price")
            .contains(tuple("product_A", 1.0), atIndex(0))
            .contains(tuple("product_B", 2.0), atIndex(1))
            .contains(tuple("product_C", 3.0), atIndex(2))
            .contains(tuple("product_D", 4.0), atIndex(3));
    }

    @Test
    void WhenProductEndpointIsHitWithPageSizeAs2AndSortPriceByDesc_thenShouldReturnProductsWithPaginationIgnoring2Products() throws JsonProcessingException {
        String response = webClient.get()
            .uri("/products?page=1&size=2&sort=price,DESC")
            .exchange()
            .expectStatus()
            .is2xxSuccessful()
            .expectBody(String.class)
            .returnResult()
            .getResponseBody();
        Assertions.assertNotNull(response);

        JsonNode pageResponse = new ObjectMapper().readValue(response, JsonNode.class);
        Assertions.assertNotNull(pageResponse);
        Assertions.assertEquals(4, pageResponse.get("totalElements")
            .asInt());
        Assertions.assertEquals(2, pageResponse.get("totalPages")
            .asInt());
        Assertions.assertTrue(pageResponse.get("last")
            .asBoolean());
        Assertions.assertFalse(pageResponse.get("first")
            .asBoolean());
        Assertions.assertEquals(2, pageResponse.get("size")
            .asInt());
        Assertions.assertEquals(2, pageResponse.get("numberOfElements")
            .asInt());
        Assertions.assertEquals(2, pageResponse.get("pageable")
            .get("offset")
            .asInt());
        Assertions.assertEquals(1, pageResponse.get("pageable")
            .get("pageNumber")
            .asInt());
        Assertions.assertEquals(2, pageResponse.get("pageable")
            .get("pageSize")
            .asInt());
        Assertions.assertTrue(pageResponse.get("pageable")
            .get("paged")
            .asBoolean());
        List<Product> content = new ObjectMapper().readValue(String.valueOf(pageResponse.get("content")), new TypeReference<List<Product>>() {
        });
        assertThat(content).hasSize(2);
        assertThat(content).extracting("name", "price")
            .contains(tuple("product_B", 2.0), atIndex(0))
            .contains(tuple("product_A", 1.0), atIndex(1));
    }
}