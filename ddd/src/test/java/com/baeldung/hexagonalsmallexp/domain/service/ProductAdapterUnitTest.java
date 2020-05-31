package com.baeldung.hexagonalsmallexp.domain.service;

import com.baeldung.hexagonalsmallexp.domain.Product;
import com.baeldung.hexagonalsmallexp.infrastructure.LocalCache;
import com.baeldung.hexagonalsmallexp.infrastructure.ProductRepostiory;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductAdapterUnitTest {

    private ProductRepostiory productRepostiory;

    private LocalCache cache;

    private ProductAdapter productAdapter;

    @BeforeEach
    void setUp() {
        productRepostiory = mock(ProductRepostiory.class);
        cache = new LocalCache();
        productAdapter = new ProductAdapter();
        productAdapter.setProductRepostiory(productRepostiory);
        productAdapter.setCache(cache);
    }

    @Test
    void shouldCreateProduct_thenSaveIt() {
        Product product = new Product(UUID.randomUUID(), 10);

        productAdapter.save(product);

        verify(productRepostiory).save(any(Product.class));
        assertTrue(!cache.CACHE.isEmpty());
    }

    @Test
    void shouldCreateProduct_thenFindIt() {
        UUID id = UUID.randomUUID();
        Product product = new Product(id, 10);
        productAdapter.save(product);
        Product findProduct = productAdapter.findByProductId(product.getId());
        assertNotNull(findProduct);
        assertEquals(findProduct.getId(), product.getId());
    }
}
