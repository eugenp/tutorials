package com.baeldung.ddd.layers.domain;

import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderProvider {
    public static Order getCreatedOrder() {
        return new Order(ObjectId.get(), new Product(UUID.randomUUID(), BigDecimal.TEN, "productName"));
    }

    public static Order getCompletedOrder() {
        final Order order = getCreatedOrder();
        order.complete();

        return order;
    }
}
