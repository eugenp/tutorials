package com.baeldung.hexagonalarchitecture.domain;

import java.util.Objects;

public class Promotion {

    private int productId;

    private double discount;

    public Promotion(int productId, double discount) {
        this.productId = productId;
        this.discount = discount;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getProductId() {
        return this.productId;
    }

    public double getDiscount() {
        return this.discount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, discount);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() == null) {
            return false;
        }

        if(obj instanceof Promotion) {
            Promotion compared = (Promotion) obj;
            return compared.productId == productId && compared.discount == discount;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Promotion{" + "productId='" + productId + '\'' + ", discount='" + discount + '\'' + "'}'";
    }
}
