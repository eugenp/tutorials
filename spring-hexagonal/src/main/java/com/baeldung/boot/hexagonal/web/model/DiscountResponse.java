package com.baeldung.boot.hexagonal.web.model;

public class DiscountResponse {
    private final double discount;

    public DiscountResponse(double discount) {
        this.discount = discount;
    }

    public double getDiscount() {
        return discount;
    }
}
