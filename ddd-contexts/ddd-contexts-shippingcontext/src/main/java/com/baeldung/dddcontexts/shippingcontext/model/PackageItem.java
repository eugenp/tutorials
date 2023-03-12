package com.baeldung.dddcontexts.shippingcontext.model;

public class PackageItem {
    private int productId;
    private float weight;
    private float estimatedValue;

    public PackageItem(int productId, float weight, float estimatedValue) {
        this.productId = productId;
        this.weight = weight;
        this.estimatedValue = estimatedValue;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getEstimatedValue() {
        return estimatedValue;
    }

    public void setEstimatedValue(float estimatedValue) {
        this.estimatedValue = estimatedValue;
    }
}
