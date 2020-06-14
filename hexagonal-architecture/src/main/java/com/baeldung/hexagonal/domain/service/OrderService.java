package com.baeldung.hexagonal.domain.service;

import com.baeldung.hexagonal.domain.Order;

public interface OrderService {
    Order createOrder(Order order);

    Order findById(Long id);



}
