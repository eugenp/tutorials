package com.baeldung.dto;

import java.util.List;

public class OrderDto {

    private String transactionId;

    private List<String> orderItemIds;

    private PaymentDto paymentDto;

    public OrderDto(String transactionId, List<String> orderItemIds, PaymentDto paymentDto) {
        this.transactionId = transactionId;
        this.orderItemIds = orderItemIds;
        this.paymentDto = paymentDto;
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

    public PaymentDto getPaymentDto() {
        return paymentDto;
    }

    public void setPaymentDto(PaymentDto paymentDto) {
        this.paymentDto = paymentDto;
    }
}
