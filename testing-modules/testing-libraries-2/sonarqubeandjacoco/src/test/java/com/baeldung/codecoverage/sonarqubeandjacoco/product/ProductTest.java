package com.baeldung.codecoverage.sonarqubeandjacoco.product;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ProductTest {

    @Test
    public void test() {
        Product product = new Product();
        product.setId(1);
        assertNull(product.getName());
        assert (product.getId() == 1);
    }

    @Test
    public void testProduct() {
        Product product = new Product(1, "product", 1, 2.0);
        assertNotNull(product.getName());
    }

}
