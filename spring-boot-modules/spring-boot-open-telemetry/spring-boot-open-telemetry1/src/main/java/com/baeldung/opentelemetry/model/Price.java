package com.baeldung.opentelemetry.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Price {

    @JsonProperty("productId")
    private long productId;

    @JsonProperty("price_amount")
    private double priceAmount;

    @JsonProperty("discount")
    private double discount;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public double getPriceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(double priceAmount) {
        this.priceAmount = priceAmount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
