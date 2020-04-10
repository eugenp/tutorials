package com.baeldung.dddmodules.shippingcontext.repository;

import com.baeldung.dddmodules.shippingcontext.model.ShippableOrder;

import java.util.Optional;

public interface ShippingOrderRepository {
    Optional<ShippableOrder> findShippableOrder(int orderId);
}
