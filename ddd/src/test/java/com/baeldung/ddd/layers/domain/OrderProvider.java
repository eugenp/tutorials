package com.baeldung.ddd.layers.domain;

import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.Arrays;

public class OrderProvider {
    public static Order getCreatedOrder() {
        return new Order(ObjectId.get(), Arrays.asList(new Product(BigDecimal.TEN, "productName")));
    }

    public static Order getCompletedOrder() {
        final Order order = getCreatedOrder();
        order.complete();

        return order;
    }
}
