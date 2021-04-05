package com.baeldung.pattern.hexagonal.product.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private String id;
    private boolean isOnSale;
    private BigDecimal price;
    private BigDecimal discountedPrice;

    public Product(String id, boolean isOnSale, BigDecimal price) {
        this.id = Objects.requireNonNull(id);
        this.price = Objects.requireNonNull(price);
        this.isOnSale = isOnSale;
    }

    public String getId() {
        return id;
    }

    public boolean isOnSale() {
        return isOnSale;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override public int hashCode() {
        return Objects.hash(id);
    }
}
