package com.baeldung.hexagonal.core;

public class CartItem {

    private final Product product;

    private final int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public int calculateBasePrice() {
        return product.getUnitPrice() * quantity;
    }

}
