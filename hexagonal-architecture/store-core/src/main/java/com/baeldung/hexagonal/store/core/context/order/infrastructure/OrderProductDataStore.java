package com.baeldung.hexagonal.store.core.context.order.infrastructure;

import com.baeldung.hexagonal.store.core.context.order.entity.OrderProductId;
import com.baeldung.hexagonal.store.core.context.order.entity.OrderProduct;

import java.util.Optional;

public interface OrderProductDataStore {
    OrderProduct save(OrderProduct orderProduct);

    Optional<OrderProduct> findById(OrderProductId orderProductPkId);
}
