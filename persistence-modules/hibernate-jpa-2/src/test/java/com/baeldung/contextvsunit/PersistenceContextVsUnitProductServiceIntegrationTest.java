package com.baeldung.contextvsunit;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.contextvsunit.entity.Product;
import com.baeldung.contextvsunit.service.PersistenceContextProductService;
import com.baeldung.contextvsunit.service.PersistenceUnitProductService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.baeldung.contextvsunit.PersistenceContextVsUnitApplication.class)
public class PersistenceContextVsUnitProductServiceIntegrationTest {

    @Autowired
    private PersistenceContextProductService persistenceContextProductService;
    @Autowired
    private PersistenceUnitProductService persistenceUnitProductService;

    @Test
    public void whenProductPersistWithTransactionPersistenceContext_thenShouldPersist() {
        Product p = new Product(1L, "Product 1", 100.0);
        persistenceContextProductService.insertProductWithTransactionTypePersistenceContext(p);

        Product productFromTransactionScoped = persistenceContextProductService.findWithTransactionTypePersistenceContext(1L);
        Assertions.assertNotNull(productFromTransactionScoped);

        Product productFromExtendedScoped = persistenceContextProductService.findWithExtendedTypePersistenceContext(1L);
        Assertions.assertNotNull(productFromExtendedScoped);
    }

    @Test
    public void whenProductPersistWithExtendedPersistence_thenShouldPersist() {
        Product product = new Product(2L, "Product 1", 100.0);
        persistenceContextProductService.insertProductWithExtendedTypePersistenceContext(product);

        Product productFromExtendedScoped = persistenceContextProductService.findWithExtendedTypePersistenceContext(2L);
        Assertions.assertNotNull(productFromExtendedScoped);

        Product productFromTransactionScoped = persistenceContextProductService.findWithTransactionTypePersistenceContext(2L);
        Assertions.assertNull(productFromTransactionScoped);
    }

    @Test
    public void whenProductPersistWithEntityManagerFactory_thenShouldPersist() {
        Product p = new Product(1L, "Product 1", 100.0);
        persistenceUnitProductService.insertProduct(p);

        Product createdProduct = persistenceUnitProductService.find(1L);
        assertNotNull(createdProduct);
    }
}
