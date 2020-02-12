package com.baeldung.cache2k;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ProductHelperUsingLoaderUnitTest {

    ProductHelperUsingLoader productHelper = new ProductHelperUsingLoader();

    @Test
    public void whenInvokedGetDiscount_thenPopulateCache() {
        assertTrue(productHelper.getDiscount("Sports") == 20);
        assertTrue(productHelper.getDiscount("Electronics") == 10);
    }

}
