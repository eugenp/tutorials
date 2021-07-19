package com.baeldung.dddhexagonalspring.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderProvider {
    public static Order getCreatedOrder() {
        return new Order(UUID.randomUUID(), new Product(UUID.randomUUID(), BigDecimal.TEN, "productName"));
    }

    public static Order getCompletedOrder() {
        final Order order = getCreatedOrder();
        order.complete();

        return order;
    }
}
