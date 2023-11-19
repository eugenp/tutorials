package com.baeldung.singleton;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceUnitTest {

    @Test
    void givenValueExistsInCache_whenGetProduct_thenDAOIsNotCalled() {
        ProductDAO productDAO = mock(ProductDAO.class);
        CacheManager cacheManager = mock(CacheManager.class);
        Product product = new Product("product1", "description");
        ProductService productService = new ProductService(productDAO, cacheManager);

        when(cacheManager.getValue(any(), any())).thenReturn(product);

        productService.getProduct("product1");

        Mockito.verify(productDAO, times(0)).getProduct(any());
    }

    @Test
    void givenValueExistsInCache_whenGetProduct_thenDAOIsNotCalled_mockingStatic() {
        ProductDAO productDAO = mock(ProductDAO.class);
        CacheManager cacheManager = mock(CacheManager.class);
        Product product = new Product("product1", "description");

        try (MockedStatic<CacheManager> cacheManagerMock = mockStatic(CacheManager.class)) {
            cacheManagerMock.when(CacheManager::getInstance).thenReturn(cacheManager);
            when(cacheManager.getValue(any(), any())).thenReturn(product);
            ProductService productService = new ProductService(productDAO);
            productService.getProduct("product1");
            Mockito.verify(productDAO, times(0)).getProduct(any());
        }
    }
}