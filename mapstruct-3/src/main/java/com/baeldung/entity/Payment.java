package com.baeldung.entity;

public class Payment {

    private String type;

    private String amount;

    public Payment() {
    }

    public Payment(String type, String amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
