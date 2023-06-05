package com.baeldung.dddcontexts.shippingcontext.repository;

import com.baeldung.dddcontexts.shippingcontext.model.ShippableOrder;

import java.util.Optional;

public interface ShippingOrderRepository {
    Optional<ShippableOrder> findShippableOrder(int orderId);
}
