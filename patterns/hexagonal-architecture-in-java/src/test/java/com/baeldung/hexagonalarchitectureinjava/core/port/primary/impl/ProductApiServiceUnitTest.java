package com.baeldung.hexagonalarchitectureinjava.core.port.primary.impl;

import com.baeldung.hexagonalarchitectureinjava.core.domain.model.Product;
import com.baeldung.hexagonalarchitectureinjava.core.port.secondary.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class ProductApiServiceUnitTest {
    private ProductRepository productRepository;
    private  ProductApiService productApiService;
    private  UUID product1Id =UUID.randomUUID();
    private  UUID product2Id =UUID.randomUUID();
    Product  product1 = new Product(product1Id,"test-prod-1","test-cat", 1);
    Product  product2 = new Product(product2Id,"test-prod-2","test-cat", 1);

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productApiService = new ProductApiService(productRepository);
    }

    @Test
    void testGetAllProducts(){
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1,product2));

        List<Product> result = productApiService.all();

        Assertions.assertEquals(Arrays.asList(product1,product2), result);
    }

    @Test
    void testFindProductsById(){
        when(productRepository.findById(product1Id)).thenReturn(product1);

        Optional<Product> result = productApiService.findById(product1Id);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(product1, result.get());
    }
}
