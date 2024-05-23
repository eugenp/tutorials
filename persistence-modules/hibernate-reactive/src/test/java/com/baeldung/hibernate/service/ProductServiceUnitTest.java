package com.baeldung.hibernate.service;

import com.baeldung.hibernate.configuration.R2DBCConfiguration;
import com.baeldung.hibernate.entities.Product;
import com.baeldung.hibernate.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.test.StepVerifier;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(classes = { R2DBCConfiguration.class, ProductService.class, ProductRepository.class })
@Transactional
public class ProductServiceUnitTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll()
            .then(productRepository.save(new Product(1L, "Product 1", "Category 1", 10.0)))
            .then(productRepository.save(new Product(2L, "Product 2", "Category 2", 15.0)))
            .then(productRepository.save(new Product(3L, "Product 3", "Category 3", 20.0)))
            .block();
    }

    @Test
    void testSave() {
        Product newProduct = new Product(4L, "Product 4", "Category 4", 24.0);

        StepVerifier.create(productService.save(newProduct))
            .assertNext(product -> {
                assertNotNull(product.getId());
                assertEquals("Product 4", product.getName());
            })
            .verifyComplete();

        StepVerifier.create(productService.findAll())
            .expectNextCount(4)
            .verifyComplete();
    }

    @Test
    void testFindAll() {
        StepVerifier.create(productService.findAll())
            .expectNextCount(3)
            .verifyComplete();
    }

}