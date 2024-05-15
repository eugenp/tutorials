package com.baeldung.singleton;

public class ProductDAO {
    public Product getProduct(String productName) {

        return new Product(productName, "description");
    }
}
