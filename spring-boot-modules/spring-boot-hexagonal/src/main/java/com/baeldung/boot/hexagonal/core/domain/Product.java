package com.baeldung.boot.hexagonal.core.domain;

import java.io.Serializable;

public class Product implements Serializable {

    private Long productId;
    private String productName;
    private Long productPrice;

    public Product(String productName) {
        super();
        this.productName = productName;
    }

    public Product(String productName, Long productPrice) {
        super();
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Long productPrice) {
        this.productPrice = productPrice;
    }
}
