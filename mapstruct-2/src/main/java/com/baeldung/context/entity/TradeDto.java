package com.baeldung.context.entity;

public class TradeDto {
    private String securityIdentifier;
    private int quantity;
    private double price;

    public TradeDto(String securityIdentifier, int quantity, double price) {
        this.securityIdentifier = securityIdentifier;
        this.quantity = quantity;
        this.price = price;
    }

    public String getSecurityIdentifier() {
        return securityIdentifier;
    }

    public void setSecurityIdentifier(String securityIdentifier) {
        this.securityIdentifier = securityIdentifier;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
