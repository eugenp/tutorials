package com.baeldung.cache2k;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ProductHelperWithEventListenerUnitTest {

    @Test
    public void whenEntryAddedInCache_thenEventListenerCalled() {
        ProductHelperWithEventListener productHelper = new ProductHelperWithEventListener();
        assertTrue(productHelper.getDiscount("Sports") == 20);
    }

}
