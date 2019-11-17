package com.baeldung.ddd.layers.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class OrderItem {
    private final UUID productId;
    private final BigDecimal price;

    public OrderItem(final Product product) {
        this.productId = product.getId();
        this.price = product.getPrice();
    }

    public UUID getProductId() {
        return productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(productId, orderItem.productId) && Objects.equals(price, orderItem.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, price);
    }
}
