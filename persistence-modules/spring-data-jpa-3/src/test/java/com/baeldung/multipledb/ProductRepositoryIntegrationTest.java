package com.baeldung.multipledb;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.multipledb.dao.product.ProductRepository;
import com.baeldung.multipledb.model.product.ProductMultipleDB;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=MultipleDbApplication.class)
@EnableTransactionManagement
public class ProductRepositoryIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Before
    @Transactional("productTransactionManager")
    public void setUp() {
        productRepository.save(ProductMultipleDB.from(1001, "Book", 21));
        productRepository.save(ProductMultipleDB.from(1002, "Coffee", 10));
        productRepository.save(ProductMultipleDB.from(1003, "Jeans", 30));
        productRepository.save(ProductMultipleDB.from(1004, "Shirt", 32));
        productRepository.save(ProductMultipleDB.from(1005, "Bacon", 10));
    }

    @Test
    public void whenRequestingFirstPageOfSizeTwo_ThenReturnFirstPage() {
        Pageable pageRequest = PageRequest.of(0, 2);

        Page<ProductMultipleDB> result = productRepository.findAll(pageRequest);

        assertThat(result.getContent(), hasSize(2));
        assertTrue(result.stream()
            .map(ProductMultipleDB::getId)
            .allMatch(id -> Arrays.asList(1001, 1002)
                .contains(id)));
    }

    @Test
    public void whenRequestingSecondPageOfSizeTwo_ThenReturnSecondPage() {
        Pageable pageRequest = PageRequest.of(1, 2);

        Page<ProductMultipleDB> result = productRepository.findAll(pageRequest);

        assertThat(result.getContent(), hasSize(2));
        assertTrue(result.stream()
            .map(ProductMultipleDB::getId)
            .allMatch(id -> Arrays.asList(1003, 1004)
                .contains(id)));
    }

    @Test
    public void whenRequestingLastPage_ThenReturnLastPageWithRemData() {
        Pageable pageRequest = PageRequest.of(2, 2);

        Page<ProductMultipleDB> result = productRepository.findAll(pageRequest);

        assertThat(result.getContent(), hasSize(1));
        assertTrue(result.stream()
            .map(ProductMultipleDB::getId)
            .allMatch(id -> Arrays.asList(1005)
                .contains(id)));
    }

    @Test
    public void whenSortingByNameAscAndPaging_ThenReturnSortedPagedResult() {
        Pageable pageRequest = PageRequest.of(0, 3, Sort.by("name"));

        Page<ProductMultipleDB> result = productRepository.findAll(pageRequest);

        assertThat(result.getContent(), hasSize(3));
        assertThat(result.getContent()
            .stream()
            .map(ProductMultipleDB::getId)
            .collect(Collectors.toList()), equalTo(Arrays.asList(1005, 1001, 1002)));

    }

    @Test
    public void whenSortingByPriceDescAndPaging_ThenReturnSortedPagedResult() {
        Pageable pageRequest = PageRequest.of(0, 3, Sort.by("price")
            .descending());

        Page<ProductMultipleDB> result = productRepository.findAll(pageRequest);

        assertThat(result.getContent(), hasSize(3));
        assertThat(result.getContent()
            .stream()
            .map(ProductMultipleDB::getId)
            .collect(Collectors.toList()), equalTo(Arrays.asList(1004, 1003, 1001)));

    }

    @Test
    public void whenSortingByPriceDescAndNameAscAndPaging_ThenReturnSortedPagedResult() {
        Pageable pageRequest = PageRequest.of(0, 5, Sort.by("price")
            .descending()
            .and(Sort.by("name")));

        Page<ProductMultipleDB> result = productRepository.findAll(pageRequest);

        assertThat(result.getContent(), hasSize(5));
        assertThat(result.getContent()
            .stream()
            .map(ProductMultipleDB::getId)
            .collect(Collectors.toList()), equalTo(Arrays.asList(1004, 1003, 1001, 1005, 1002)));

    }

    @Test
    public void whenRequestingFirstPageOfSizeTwoUsingCustomMethod_ThenReturnFirstPage() {
        Pageable pageRequest = PageRequest.of(0, 2);

        List<ProductMultipleDB> result = productRepository.findAllByPrice(10, pageRequest);

        assertThat(result, hasSize(2));
        assertTrue(result.stream()
            .map(ProductMultipleDB::getId)
            .allMatch(id -> Arrays.asList(1002, 1005)
                .contains(id)));
    }
}
