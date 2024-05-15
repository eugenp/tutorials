package com.baeldung.cache2k;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ProductHelperUsingLoaderUnitTest {

    @Test
    public void whenInvokedGetDiscount_thenPopulateCacheUsingLoader() {
        ProductHelperUsingLoader productHelper = new ProductHelperUsingLoader();
        assertTrue(productHelper.getCacheMissCount() == 0);

        assertTrue(productHelper.getDiscount("Sports") == 20);
        assertTrue(productHelper.getCacheMissCount() == 1);

        assertTrue(productHelper.getDiscount("Electronics") == 10);
        assertTrue(productHelper.getCacheMissCount() == 2);
    }

}
