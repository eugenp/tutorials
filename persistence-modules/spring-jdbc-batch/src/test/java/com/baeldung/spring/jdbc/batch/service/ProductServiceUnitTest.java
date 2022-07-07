package com.baeldung.spring.jdbc.batch.service;

import com.baeldung.spring.jdbc.batch.repo.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Clock;
import java.util.Random;

class ProductServiceUnitTest {

    @Mock
    ProductRepository productRepository;
    @Mock
    Random random;
    @Mock
    Clock clock;
    @InjectMocks
    ProductService productService;

    @Test
    void testWhenThen() {

    }
}