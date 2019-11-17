package com.baeldung.ddd.layers.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderItem {
    private final Product product;

    public OrderItem(final Product product) {
        this.product = product;
    }

    public BigDecimal getPrice() {
        return product.getPrice();
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(product, orderItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product);
    }
}
