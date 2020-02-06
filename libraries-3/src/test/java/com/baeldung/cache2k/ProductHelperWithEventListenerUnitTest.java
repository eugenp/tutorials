package com.baeldung.cache2k;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ProductHelperWithEventListenerUnitTest {

    ProductHelperWithEventListener productHelper = new ProductHelperWithEventListener();

    @Test
    public void whenEntryAddedInCache_thenEventListenerCalled() {
        assertTrue(productHelper.getDiscount("Sports") == 20);
    }

}
