package com.baeldung.patterns.hexagonal;

import com.baeldung.patterns.hexagonal.application.SimpleToStringOutputStrategy;
import com.baeldung.patterns.hexagonal.application.adaptor.CommandLineAdaptor;
import com.baeldung.patterns.hexagonal.domain.port.ProductRepository;
import com.baeldung.patterns.hexagonal.domain.port.ProductService;
import com.baeldung.patterns.hexagonal.domain.ProductServiceImpl;
import com.baeldung.patterns.hexagonal.domain.model.Product;
import com.baeldung.patterns.hexagonal.infrastructure.adapter.InMemoryProductRepository;

import org.junit.Test;
import org.junit.runner.RunWith;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HexagonalUnitTest {
    @Mock
    private ProductRepository mockProductRepository;

    @Test
    public void givenTestAppAndMockInfraAdapters_whenProductDetailsAreRequested_thenProductDetailsAreReturnedFromMockRepo() throws Exception {
        ProductService productService = new ProductServiceImpl(mockProductRepository);
        Product myProduct = new Product("1234", "myProduct");

        given(mockProductRepository.findProduct("1234")).willReturn(myProduct);

        assertThat(productService.getProduct("1234")).isEqualTo(myProduct);
    }

    @Test
    public void givenTestAppAndInMemoryInfraAdapters_whenProductDetailsAreRequested_thenProductDetailsAreReturnedFromInMemoryRepo() throws Exception {
        Product product1 = new Product("1", "product1");
        Product product2 = new Product("2", "product2");
        Product product3 = new Product("3", "product3");

        InMemoryProductRepository inMemoryProductRepository = new InMemoryProductRepository();
        inMemoryProductRepository.addProducts(asList(product1, product2, product3));

        ProductService productService = new ProductServiceImpl(inMemoryProductRepository);

        assertThat(productService.getProduct("2")).isEqualTo(product2);
    }

    @Test
    public void givenConsoleAppAndInMemoryInfraAdapters_whenProductDetailsAreRequested_thenProductDetailsAreReturnedFromInMemoryRepo() throws Exception {
        Product product1 = new Product("1", "product1");
        Product product2 = new Product("2", "product2");
        Product product3 = new Product("3", "product3");

        InMemoryProductRepository inMemoryProductRepository = new InMemoryProductRepository();
        inMemoryProductRepository.addProducts(asList(product1, product2, product3));

        CommandLineAdaptor commandLineAdapter
                = new CommandLineAdaptor(
                        new ProductServiceImpl(inMemoryProductRepository));

        assertThat(commandLineAdapter.getProduct(new String[]{"3"})).isEqualTo("Product{id='3', name='product3'}");
    }

    @Test
    public void givenConsoleAppAndMockInfraAdapters_whenProductDetailsAreRequested_thenProductDetailsAreReturnedFromMockMemoryRepo() throws Exception {
        Product myProduct = new Product("1", "myProduct");
        CommandLineAdaptor commandLineAdapter
                = new CommandLineAdaptor(
                        new ProductServiceImpl(mockProductRepository));

        given(mockProductRepository.findProduct("1")).willReturn(myProduct);

        assertThat(commandLineAdapter.getProduct(new String[]{"1"})).isEqualTo("Product{id='1', name='myProduct'}");
    }
}
