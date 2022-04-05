package com.baeldung.dddmodules.ordercontext.model;

public class OrderItem {
    private int productId;
    private int quantity;
    private float unitPrice;
    private float unitWeight;

    public OrderItem(int productId, int quantity, float unitPrice, float unitWeight) {
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.unitWeight = unitWeight;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotalPrice() {
        return this.quantity * this.unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getUnitWeight() {
        return unitWeight;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitWeight(float unitWeight) {
        this.unitWeight = unitWeight;
    }
}
