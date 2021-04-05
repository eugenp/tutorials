package com.baeldung.pattern.hexagonal.product.adapter.inbound.web;

import java.math.BigDecimal;
import java.util.Objects;

public class PriceResource {
    private String productId;
    private BigDecimal price;

    public PriceResource(String productId, BigDecimal price) {
        this.productId = productId;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceResource that = (PriceResource) o;
        return productId.equals(that.productId) &&
                price.equals(that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, price);
    }
}
