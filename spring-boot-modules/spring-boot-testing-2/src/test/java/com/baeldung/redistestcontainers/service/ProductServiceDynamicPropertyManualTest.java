package com.baeldung.redistestcontainers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.baeldung.redistestcontainers.hash.Product;
import com.redis.testcontainers.RedisContainer;

@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
public class ProductServiceDynamicPropertyManualTest {

    @Autowired
    private ProductService productService;

    @Container
    private static final RedisContainer REDIS_CONTAINER = new RedisContainer(DockerImageName.parse("redis:5.0.3-alpine")).withExposedPorts(6379);

    @DynamicPropertySource
    private static void registerRedisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.redis.port", () -> REDIS_CONTAINER.getMappedPort(6379)
            .toString());
    }

    @Test
    void givenRedisContainerConfiguredWithDynamicProperties_whenCheckingRunningStatus_thenStatusIsRunning() {
        assertTrue(REDIS_CONTAINER.isRunning());
    }

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
