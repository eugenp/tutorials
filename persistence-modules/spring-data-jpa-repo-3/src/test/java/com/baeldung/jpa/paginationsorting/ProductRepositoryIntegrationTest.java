package com.baeldung.jpa.paginationsorting;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.jpa.paginationsorting.model.Product;
import com.baeldung.jpa.paginationsorting.repository.ProductRepository;

@SpringBootTest(classes = PaginationSortingApplication.class)
@EnableTransactionManagement
class ProductRepositoryIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    @Transactional("productTransactionManager")
    void setUp() {
        productRepository.save(Product.from(1001, "Book", 21));
        productRepository.save(Product.from(1002, "Coffee", 10));
        productRepository.save(Product.from(1003, "Jeans", 30));
        productRepository.save(Product.from(1004, "Shirt", 32));
        productRepository.save(Product.from(1005, "Bacon", 10));
    }

    @Test
    void whenRequestingFirstPageOfSizeTwo_ThenReturnFirstPage() {
        Pageable pageRequest = PageRequest.of(0, 2);

        Page<Product> result = productRepository.findAll(pageRequest);

        assertThat(result.getContent(), hasSize(2));
        assertTrue(result.stream()
            .map(Product::getId)
            .allMatch(id -> Arrays.asList(1001, 1002)
                .contains(id)));
    }

    @Test
    void whenRequestingSecondPageOfSizeTwo_ThenReturnSecondPage() {
        Pageable pageRequest = PageRequest.of(1, 2);

        Page<Product> result = productRepository.findAll(pageRequest);

        assertThat(result.getContent(), hasSize(2));
        assertTrue(result.stream()
            .map(Product::getId)
            .allMatch(id -> Arrays.asList(1003, 1004)
                .contains(id)));
    }

    @Test
    void whenRequestingLastPage_ThenReturnLastPageWithRemData() {
        Pageable pageRequest = PageRequest.of(2, 2);

        Page<Product> result = productRepository.findAll(pageRequest);

        assertThat(result.getContent(), hasSize(1));
        assertTrue(result.stream()
            .map(Product::getId)
            .allMatch(id -> Arrays.asList(1005)
                .contains(id)));
    }

    @Test
    void whenSortingByNameAscAndPaging_ThenReturnSortedPagedResult() {
        Pageable pageRequest = PageRequest.of(0, 3, Sort.by("name"));

        Page<Product> result = productRepository.findAll(pageRequest);

        assertThat(result.getContent(), hasSize(3));
        assertThat(result.getContent()
            .stream()
            .map(Product::getId)
            .collect(Collectors.toList()), equalTo(Arrays.asList(1005, 1001, 1002)));

    }

    @Test
    void whenSortingByPriceDescAndPaging_ThenReturnSortedPagedResult() {
        Pageable pageRequest = PageRequest.of(0, 3, Sort.by("price")
            .descending());

        Page<Product> result = productRepository.findAll(pageRequest);

        assertThat(result.getContent(), hasSize(3));
        assertThat(result.getContent()
            .stream()
            .map(Product::getId)
            .collect(Collectors.toList()), equalTo(Arrays.asList(1004, 1003, 1001)));

    }

    @Test
    void whenSortingByPriceDescAndNameAscAndPaging_ThenReturnSortedPagedResult() {
        Pageable pageRequest = PageRequest.of(0, 5, Sort.by("price")
            .descending()
            .and(Sort.by("name")));

        Page<Product> result = productRepository.findAll(pageRequest);

        assertThat(result.getContent(), hasSize(5));
        assertThat(result.getContent()
            .stream()
            .map(Product::getId)
            .collect(Collectors.toList()), equalTo(Arrays.asList(1004, 1003, 1001, 1005, 1002)));

    }

    @Test
    void whenRequestingFirstPageOfSizeTwoUsingCustomMethod_ThenReturnFirstPage() {
        Pageable pageRequest = PageRequest.of(0, 2);

        List<Product> result = productRepository.findAllByPrice(10, pageRequest);

        assertThat(result, hasSize(2));
        assertTrue(result.stream()
            .map(Product::getId)
            .allMatch(id -> Arrays.asList(1002, 1005)
                .contains(id)));
    }
}
