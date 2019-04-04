package com.baeldung.domain;

/**
 * Domain object representing the order created by the application. 
 */
public class Order {
    private long orderId;
    private String productName;

    public Order(long orderId, String productName) {
        this.orderId = orderId;
        this.productName = productName;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
