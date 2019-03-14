package com.baeldung.hexagonal.store.core.context.order.service;

import com.baeldung.hexagonal.store.core.context.customer.exception.CustomerNotFoundException;
import com.baeldung.hexagonal.store.core.context.customer.exception.NotEnoughFundsException;
import com.baeldung.hexagonal.store.core.context.order.exception.ProductNotFoundException;
import com.baeldung.hexagonal.store.core.context.order.entity.Order;

import java.util.Map;
import java.util.Optional;

public interface OrderService {
    Iterable<Order> getAllOrders();

    Optional<Order> getOrderById(int id);

    Optional<Order> processNewCustomerOrder(long customerId, Map<Long, Integer> productQuantityMap) throws CustomerNotFoundException, ProductNotFoundException, NotEnoughFundsException;
}
