package com.baeldung.hexagonal.store.core.context.order.infrastructure;

import com.baeldung.hexagonal.store.core.context.order.entity.Order;

public interface OrderDataStore {
    Order save(Order orderProduct);
}
