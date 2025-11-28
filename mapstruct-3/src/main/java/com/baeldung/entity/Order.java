package com.baeldung.entity;

import java.util.List;

public class Order {

    private String transactionId;

    private List<String> orderItemIds;

    private Payment payment;

    public Order(String transactionId, List<String> orderItemIds, Payment payment) {
        this.transactionId = transactionId;
        this.orderItemIds = orderItemIds;
        this.payment = payment;
    }

    public Order() {
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public List<String> getOrderItemIds() {
        return orderItemIds;
    }

    public void setOrderItemIds(List<String> orderItemIds) {
        this.orderItemIds = orderItemIds;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
