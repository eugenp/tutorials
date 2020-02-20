package com.baeldung.hexagonal.ports.driver;

import com.baeldung.hexagonal.adapter.driven.EmailNotifyingAdapter;
import com.baeldung.hexagonal.adapter.driven.ProductRepositoryAdapter;
import com.baeldung.hexagonal.adapter.driver.ProductCreator;
import com.baeldung.hexagonal.adapter.driver.ProductProvider;
import com.baeldung.hexagonal.bridge.ProductRequest;
import com.baeldung.hexagonal.bridge.ProductResponse;
import com.baeldung.hexagonal.bridge.ProductService;
import com.baeldung.hexagonal.domain.ProductVO;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestSuite {

    ProductService bridge = new ProductService(new EmailNotifyingAdapter());

    IProvide<ProductRequest, ProductResponse> provider = new ProductProvider(bridge);
    IModify<ProductRequest, ProductResponse> creator = new ProductCreator(bridge);

    @BeforeClass public static void setup() {
        ProductRepositoryAdapter.getInstance().create(new ProductVO("PS"));
        ProductRepositoryAdapter.getInstance().create(new ProductVO("XBOX360"));
    }

    @Test public void givenProductsExist_whenRequested_thenShouldList() {
        assertEquals(provider.list().size(), 2);
    }

    @Test public void givenProductsExist_whenAskedToCreateMore_thenShouldNotifyAndPersist() {
        //when
        ProductVO productToAdd = new ProductVO("Nintendo");
        creator.execute(new ProductRequest(productToAdd));
        //then
        boolean exists = provider.list().stream().anyMatch(productResponse -> productResponse.getProduct().getName().equals(productToAdd.getName()));

        assertTrue(exists);
        assertEquals(1, creator.getBridge().getNotifier().getCount());
        assertEquals(3, provider.list().size());

    }
}
