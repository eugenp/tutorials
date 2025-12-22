package com.baeldung.dto;

import java.util.List;

public class OrderDto {

    private String transactionId;

    private List<String> orderItemIds;

    private PaymentDto payment;

    public OrderDto(String transactionId, List<String> orderItemIds, PaymentDto payment) {
        this.transactionId = transactionId;
        this.orderItemIds = orderItemIds;
        this.payment = payment;
    }

    public OrderDto() {
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

    public PaymentDto getPayment() {
        return payment;
    }

    public void setPayment(PaymentDto payment) {
        this.payment = payment;
    }
}
