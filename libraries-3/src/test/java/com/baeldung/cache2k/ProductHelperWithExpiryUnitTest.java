package com.baeldung.cache2k;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ProductHelperWithExpiryUnitTest {

    ProductHelperWithExpiry productHelper = new ProductHelperWithExpiry();

    @Test
    public void whenInvokedGetDiscountForExpiredProduct_thenNoDiscount() throws InterruptedException {
        assertTrue(productHelper.getDiscount("Sports") == 20);
        Thread.sleep(20);
        assertTrue(productHelper.getDiscount("Sports") == 0);
    }

}
