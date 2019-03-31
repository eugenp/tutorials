package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.domain.Product;
import com.baeldung.hexagonal.domain.Purchase;

public class PurchaseByCash implements Purchase {

        public boolean purchaseProduct(Product product) {
                return product.getPrice() <= 100;
        }
}
