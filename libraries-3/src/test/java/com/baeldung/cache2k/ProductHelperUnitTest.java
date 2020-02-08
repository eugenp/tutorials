package com.baeldung.cache2k;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ProductHelperUnitTest {

    ProductHelper productHelper = new ProductHelper();

    @Test
    public void whenInvokedGetDiscount_thenGetItFromCache() {
        assertTrue(productHelper.getDiscount("Sports") == 20);
        assertTrue(productHelper.getDiscount("Electronics") == 0);
    }

}
