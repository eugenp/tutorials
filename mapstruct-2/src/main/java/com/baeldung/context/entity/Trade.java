package com.baeldung.context.entity;

public class Trade {
    private String securityID;
    private int quantity;
    private double price;

    public Trade(String securityID, int quantity, double price) {
        this.securityID = securityID;
        this.quantity = quantity;
        this.price = price;
    }

    public String getSecurityID() {
        return securityID;
    }

    public void setSecurityID(String securityID) {
        this.securityID = securityID;
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
