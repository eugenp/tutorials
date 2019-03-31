package com.baeldung.adapters;

import com.baeldung.domain.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PurchaseByCashTest {

    @Test
    void shouldPurchaseProductBelowLimit() {
        Product product = new Product("book", 100);
        PurchaseByCash purchase = new PurchaseByCash();

        assertTrue(purchase.purchaseProduct(product));
    }

    @Test
    void shouldNotPurchaseProductAboveLimit() {
        Product product = new Product("car", 1000);
        PurchaseByCash purchase = new PurchaseByCash();

        assertFalse(purchase.purchaseProduct(product));
    }
}