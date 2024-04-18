package com.baeldung.spring.data.jpa.queryjsonb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Propagation;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "/testdata.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ProductRepositoryLiveTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void whenFindByAttribute_thenReturnTheObject() {
        List<Product> redProducts = productRepository.findByAttribute("color", "red");

        assertEquals(1, redProducts.size());
        assertEquals("Laptop", redProducts.get(0).getName());
    }

    @Test
    void whenFindByNestedAttribute_thenReturnTheObject() {
        List<Product> electronicProducts = productRepository.findByNestedAttribute("details", "category", "electronics");

        assertEquals(1, electronicProducts.size());
        assertEquals("Headphones", electronicProducts.get(0)
            .getName());
    }

    @Test
    void whenFindByJsonPath_thenReturnTheObject() {
        List<Product> redProducts = productRepository.findByJsonPath("color", "red");
        assertEquals(1, redProducts.size());
        assertEquals("Laptop", redProducts.get(0)
            .getName());
    }

    @Test
    void givenNestedJsonAttribute_whenFindByJsonPath_thenReturnTheObject() {
       List<Product> electronicProducts = productRepository.findByNestedJsonPath("details", "category", "electronics");
       assertEquals(1, electronicProducts.size());
       assertEquals("Headphones", electronicProducts.get(0)
            .getName());
    }

    @Test
    void whenUsingJPASpecification_thenReturnTheObject() {
        ProductSpecification spec = new ProductSpecification("color", "red");
        Page<Product> redProducts = productRepository.findAll(spec, Pageable.unpaged());

        assertEquals(1, redProducts.getContent()
            .size());
        assertEquals("Laptop", redProducts.getContent()
            .get(0)
            .getName());
    }
}