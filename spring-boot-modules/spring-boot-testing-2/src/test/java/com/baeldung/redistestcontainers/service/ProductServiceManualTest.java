package com.baeldung.redistestcontainers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import com.baeldung.redistestcontainers.hash.Product;

/**
 * Requires Docker running on the machine to run without errors
 * Therefore, skipped from pipeline
 */
@SpringBootTest
public class ProductServiceManualTest {

    static {
        GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:5.0.3-alpine"))
          .withExposedPorts(6379);
        redis.start();
        System.setProperty("spring.redis.host", redis.getHost());
        System.setProperty("spring.redis.port", redis.getMappedPort(6379).toString());
    }

    @Autowired
    private ProductService productService;

    @Test
    void givenProductCreated_whenGettingProductById_thenProductExistsAndHasSameProperties() {
        Product product = new Product("1", "Test Product", 10.0);
        productService.createProduct(product);
        Product productFromDb = productService.getProduct("1");
        assertEquals("1", productFromDb.getId());
        assertEquals("Test Product", productFromDb.getName());
        assertEquals(10.0, productFromDb.getPrice());
    }

    @Test
    void givenProductCreatedAndUpdated_whenGettingTheProduct_thenUpdatedProductReturned() {
        Product product = new Product("1", "Test Product", 10.0);
        productService.createProduct(product);
        Product productFromDb = productService.getProduct("1");
        assertEquals("1", productFromDb.getId());
        assertEquals("Test Product", productFromDb.getName());
        assertEquals(10.0, productFromDb.getPrice());
        productFromDb.setName("Updated Product");
        productFromDb.setPrice(20.0);
        productService.updateProduct(productFromDb);
        Product updatedProductFromDb = productService.getProduct("1");
        assertEquals("Updated Product", updatedProductFromDb.getName());
        assertEquals(20.0, updatedProductFromDb.getPrice());
    }

    @Test
    void givenProductCreatedAndDeleted_whenGettingTheProduct_thenNoProductReturned() {
        Product product = new Product("1", "Test Product", 10.0);
        productService.createProduct(product);
        Product productFromDb = productService.getProduct("1");
        assertEquals("1", productFromDb.getId());
        assertEquals("Test Product", productFromDb.getName());
        assertEquals(10.0, productFromDb.getPrice());
        productService.deleteProduct("1");
        Product deletedProductFromDb = productService.getProduct("1");
        assertNull(deletedProductFromDb);
    }

    @Test
    void givenProductCreated_whenGettingProductById_thenSameProductReturned() {
        Product product = new Product("1", "Test Product", 10.0);
        productService.createProduct(product);
        Product productFromDb = productService.getProduct("1");
        assertEquals("1", productFromDb.getId());
        assertEquals("Test Product", productFromDb.getName());
        assertEquals(10.0, productFromDb.getPrice());
    }
}
