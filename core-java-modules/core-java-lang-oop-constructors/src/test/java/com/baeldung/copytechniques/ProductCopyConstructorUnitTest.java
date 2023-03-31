package com.baeldung.copyconstructor;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class ProductCopyConstructorUnitTest {

    @Test
    public void givenCopyConstructor_whenDeepCopy_thenDistinct() {
        Product product = new Product("P1", "MacBook Pro", 55000.70);
        Product productCopy = new Product(product);
        product.setProductName("MacBook Air");

        assertNotEquals(product.getProductName(), productCopy.getProductName()); }
}
