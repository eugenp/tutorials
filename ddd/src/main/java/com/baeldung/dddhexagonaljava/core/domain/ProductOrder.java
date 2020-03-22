package com.baeldung.dddhexagonaljava.core.domain;

public class ProductOrder {
    private Product product;
    private int quantity;

    public ProductOrder(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
