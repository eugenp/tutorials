package com.baeldung.dto;

public class PaymentDto {

    private String type;

    private Double amount;

    public PaymentDto() {
    }

    public PaymentDto(String type, Double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
