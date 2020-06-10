package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.domain.Order;

public interface OrderService {

    Order applyVAT(Order order);
}
