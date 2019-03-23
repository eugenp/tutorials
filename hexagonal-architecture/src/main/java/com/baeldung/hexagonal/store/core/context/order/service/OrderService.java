package com.baeldung.hexagonal.store.core.context.order.service;

import com.baeldung.hexagonal.store.core.context.order.entity.Order;

import java.util.Map;
import java.util.Optional;

public interface OrderService {
    Optional<Order> processNewCustomerOrder(long customerId, Map<Long, Integer> productQuantityMap) throws RuntimeException;
}
