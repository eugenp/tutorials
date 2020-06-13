package com.baeldung.univocity.model;

import com.univocity.parsers.annotations.Parsed;

public class Product {

    @Parsed(field = "product_no")
    private String productNumber;

    @Parsed
    private String description;

    @Parsed(field = "unit_price")
    private float unitPrice;

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "Product [Product Number: " + productNumber + ", Description: " + description + ", Unit Price: " + unitPrice + "]";
    }
}
