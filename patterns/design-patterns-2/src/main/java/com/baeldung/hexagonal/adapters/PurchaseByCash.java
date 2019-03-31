package com.baeldung.adapters;

import com.baeldung.domain.Product;
import com.baeldung.domain.Purchase;

public class PurchaseByCash implements Purchase {

    public boolean purchaseProduct(Product product) {
        return product.getPrice() <= 100;
    }
}