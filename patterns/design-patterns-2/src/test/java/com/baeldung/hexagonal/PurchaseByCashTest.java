package com.baeldung.hexagonal;

import com.baeldung.hexagonal.adapters.PurchaseByCash;
import com.baeldung.hexagonal.domain.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
