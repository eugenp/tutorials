package com.baeldung.dto;

public class PaymentDto {

    private String type;

    public PaymentDto() {
    }

    public PaymentDto(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
