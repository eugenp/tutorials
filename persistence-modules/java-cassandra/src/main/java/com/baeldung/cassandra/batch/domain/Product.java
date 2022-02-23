package com.baeldung.cassandra.batch.domain;

import java.util.UUID;

public class Product {

    public Product() {
        super();
    }

    public Product(UUID productId, UUID variantId, String productName, String description, float price) {
        super();
        this.productId = productId;
        this.variantId = variantId;
        this.productName = productName;
        this.description = description;
        this.price = price;
	}

	public Product(UUID productId, String productName, String description, float price) {
        super();
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
	}

    private UUID productId;
    private UUID variantId;
    private String productName;
    private String description;
    private float price;
    
    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }
    
    public UUID getVariantId() {
        return variantId;
    }

    public void setVariantId(UUID variantId) {
        this.variantId = variantId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
	
	@Override
    public String toString() {
        return "Product [productId=" + productId + ", variantId=" + variantId + ", productName=" + productName
            + ", description=" + description + ", price=" + price + "]";
    }	   
}
