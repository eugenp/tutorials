package com.baeldung.hexagonal.architecture.domain.model;

import java.util.Objects;

/**
 * The class defines the domain object model
 */
public class Product {

    private Integer productId;
    private String type;
    private String description;


    public Product() {
        super();
    }

    public Product(Integer productId, String type, String description) {
        this.productId = productId;
        this.type = type;
        this.description = description;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
