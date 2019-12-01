package com.baeldung.patterns.hexagonal.domain;

import com.baeldung.patterns.hexagonal.domain.exception.ProductNotFoundException;
import com.baeldung.patterns.hexagonal.domain.model.Product;
import com.baeldung.patterns.hexagonal.domain.port.ProductRepository;
import com.baeldung.patterns.hexagonal.domain.port.ProductService;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplUnitTest {

    @Mock
    private ProductRepository mockProductRepository;

    private ProductService  productService;

    @Before
    public void setup() {
        productService = new ProductServiceImpl(mockProductRepository);
    }

    @Test
    public void whenAddProductsRequested_thenProductsAreAddedToRepository() {
        Product product1 = new Product("1", "product1");
        Product product2 = new Product("2", "product2");

        productService.addProducts(asList(product1, product2));

        then(mockProductRepository).should().addProducts(asList(product1, product2));
    }

    @Test (expected = ProductNotFoundException.class)
    public void givenProductIsUnavailable_whenProductRequested_thenExceptionIsThrown() throws Exception {
        given(mockProductRepository.findProduct("1")).willReturn(null);

        productService.getProduct("1");
    }

    @Test
    public void givenProductIsAvailable_whenProductRequested_thenProductIsReturned() throws Exception {
        Product product1 = new Product("1", "product1");
        given(mockProductRepository.findProduct("1")).willReturn(product1);

        assertThat(productService.getProduct("1")).isEqualTo(product1);
    }

}