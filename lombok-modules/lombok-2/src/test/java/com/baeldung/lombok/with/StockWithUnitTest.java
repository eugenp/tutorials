package com.baeldung.lombok.with;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StockWithUnitTest {

    @Test
    public void givenWithManuallyProvided_whenSkuChanged_thenManualMethodUsed() {
        Stock stock = new Stock("sku-001", 5);

        Stock modifiedStock = stock.withSku("sku-002");
        Stock anotherModifiedStock = stock.withSKU("sku-003", "sku-004");

        assertNotSame(modifiedStock, stock);
        assertNotSame(anotherModifiedStock, stock);
        assertTrue(modifiedStock.getSku().startsWith("mod"));
        assertTrue(anotherModifiedStock.getSku().startsWith("mod"));
    }
}