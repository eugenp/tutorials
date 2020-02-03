package com.baeldung.hexagonal.ports.driver;

import com.baeldung.hexagonal.adapter.driven.EmailNotifyingAdapter;
import com.baeldung.hexagonal.adapter.driven.ProductRepositoryAdapter;
import com.baeldung.hexagonal.adapter.driver.ProductCreator;
import com.baeldung.hexagonal.adapter.driver.ProductProvider;
import com.baeldung.hexagonal.bridge.ProductRequest;
import com.baeldung.hexagonal.bridge.ProductService;
import com.baeldung.hexagonal.domain.ProductVO;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestSuite {

    ProductService productService = new ProductService();

    ProductProvider productProvider = new ProductProvider(productService);
    ProductCreator productCreator = new ProductCreator(productService, new EmailNotifyingAdapter());

    @BeforeClass public static void setup() {
        ProductRepositoryAdapter.getInstance().create(new ProductVO("PS"));
        ProductRepositoryAdapter.getInstance().create(new ProductVO("XBOX360"));
    }

    @Test public void givenProductsExist_whenAskedToList_thenShouldListThem() {
        assertEquals(productProvider.list().size(), 2);
    }

    @Test
    public void givenProductsExist_whenAskedToCreateMore_thenShouldNotifyAndPersist() {
        //when
        ProductVO productToAdd = new ProductVO("Nintendo");
        productCreator.execute(new ProductRequest(productToAdd));
        //then
        boolean exists = productProvider.list()
                .stream()
                .anyMatch(productResponse -> productResponse.getProduct().getName().equals(productToAdd.getName()));

        assertTrue(exists);
        assertEquals(1, productCreator.getNotifier().getCount());
        assertEquals(3, productProvider.list().size());

    }
}
