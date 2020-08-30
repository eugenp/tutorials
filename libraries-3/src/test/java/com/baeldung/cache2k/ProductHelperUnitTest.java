package com.baeldung.cache2k;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ProductHelperUnitTest {

    @Test
    public void whenInvokedGetDiscountTwice_thenGetItFromCache() {
        ProductHelper productHelper = new ProductHelper();
        assertTrue(productHelper.getCacheMissCount() == 0);
        assertTrue(productHelper.getDiscount("Sports") == 20);
        assertTrue(productHelper.getDiscount("Sports") == 20);
        assertTrue(productHelper.getCacheMissCount() == 1);
    }

}
