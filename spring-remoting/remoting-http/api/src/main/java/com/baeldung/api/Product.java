package com.baeldung.api;

import java.io.Serializable;

public class Product implements Serializable {

    private String productCode;

    public Product(String productCode) {
        this.productCode = productCode;
    }

    @Override public String toString() {
        return "Product: " + productCode;
    }
}
