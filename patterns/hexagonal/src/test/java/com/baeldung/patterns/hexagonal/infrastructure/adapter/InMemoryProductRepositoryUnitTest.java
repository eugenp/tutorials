package com.baeldung.patterns.hexagonal.infrastructure.adapter;

import com.baeldung.patterns.hexagonal.domain.model.Product;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;

import static java.util.Arrays.asList;
import static java.util.Collections.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class InMemoryProductRepositoryUnitTest {
    private InMemoryProductRepository repository;

    @Before
    public void setup() {
        repository = new InMemoryProductRepository();
    }

    @Test
    public void givenRepoIsEmpty_whenProductIsRequested_thenNullIsReturned() {
        assertThat(repository.findProduct("1")).isNull();
    }

    @Test
    public void givenProductIsUnavailable_whenProductIsRequested_thenNullIsReturned() {
        Product product1 = new Product("1", "product1");
        Product product2 = new Product("2", "product2");
        repository.addProducts(asList(product1, product2));

        assertThat(repository.findProduct("3")).isNull();
    }

    @Test
    public void givenProductIsAvailable_whenProductIsRequested_thenProductIsReturned() {
        Product product1 = new Product("1", "product1");
        Product product2 = new Product("2", "product2");
        repository.addProducts(asList(product1, product2));

        assertThat(repository.findProduct("1")).isEqualTo(product1);
    }
}