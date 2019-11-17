package com.baeldung.ddd.layers.domain;

import org.bson.types.ObjectId;

import java.math.BigDecimal;

public class OrderProvider {
    public static Order getCreatedOrder() {
        return new Order(ObjectId.get(), new Product(BigDecimal.TEN, "productName"));
    }

    public static Order getCompletedOrder() {
        final Order order = getCreatedOrder();
        order.complete();

        return order;
    }
}
