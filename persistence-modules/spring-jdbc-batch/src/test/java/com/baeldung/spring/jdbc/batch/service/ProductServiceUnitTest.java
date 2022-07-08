package com.baeldung.spring.jdbc.batch.service;

import com.baeldung.spring.jdbc.batch.model.Product;
import com.baeldung.spring.jdbc.batch.repo.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

@ExtendWith(MockitoExtension.class)
class ProductServiceUnitTest {

    ProductRepository productRepository;
    Random random;
    Clock clock;
    ProductService productService;

    @Captor
    ArgumentCaptor<List<Product>> proArgumentCaptor;


    @BeforeEach
    void setUp() {
        this.productRepository = mock(ProductRepository.class);
        this.random = mock(Random.class, withSettings().withoutAnnotations());
        this.clock = mock(Clock.class);
        this.productService = new ProductService(this.productRepository, this.random, this.clock);
    }


    @Test
    void testWhenCreateProductsThenShouldSaveAndReturnElapsedTime() {
        when(random.nextInt(4))
          .thenReturn(1, 3, 2, 0);
        when(clock.instant())
          .thenReturn(Instant.parse("2022-04-09T10:15:30.00Z"));
        when(clock.getZone())
          .thenReturn(ZoneId.of("UTC"));

        when(clock.millis())
          .thenReturn(100L,500L);


        final long actualElapsedTime = productService.createProducts(2);


        assertThat(actualElapsedTime)
          .isEqualTo(400L);

        verify(productRepository,times(1))
          .saveAll(proArgumentCaptor.capture());

        assertThat(proArgumentCaptor.getValue())
          .hasSize(2)
          .extracting("title", "createdTs", "price")
          .containsExactly(
            tuple("yacht", LocalDateTime.parse("2022-04-09T10:15:30"), new BigDecimal("8539.99")),
            tuple("car", LocalDateTime.parse("2022-04-09T10:15:30"), new BigDecimal("88894"))
          );
    }
}