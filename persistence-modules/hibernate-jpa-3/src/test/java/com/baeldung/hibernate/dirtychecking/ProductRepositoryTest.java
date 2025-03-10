package com.baeldung.hibernate.dirtychecking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.hibernate.dirtychecking.entity.Product;
import com.baeldung.hibernate.dirtychecking.repository.ProductRepository;

import jakarta.persistence.EntityManager;

@SpringBootTest
@ActiveProfiles("dirtychecking")
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void givenProduct_whenModifiedWithoutSave_thenAssertChangesPersisted() {
        Product product = new Product("LOREM", 100.00);
        productRepository.save(product);

        product.setPrice(80.00);
        entityManager.flush();
        entityManager.clear();

        Product updatedProduct = productRepository.findByCode("LOREM").orElseThrow(RuntimeException::new);
        assertEquals(80.00, updatedProduct.getPrice());
    }

    @Test
    @Transactional
    void givenDetachedProduct_whenModifiedWithoutSave_thenAssertChangesNotPersisted() {
        Product product = new Product("LOREM", 100.00);
        productRepository.save(product);

        entityManager.detach(product);
        product.setPrice(80.00);
        entityManager.flush();
        entityManager.clear();

        Product updatedProduct = productRepository.findByCode("LOREM").orElseThrow(RuntimeException::new);
        assertEquals(100.00, updatedProduct.getPrice());
    }

}
