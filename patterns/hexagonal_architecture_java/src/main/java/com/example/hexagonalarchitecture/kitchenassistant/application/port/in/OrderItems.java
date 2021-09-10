package com.example.hexagonalarchitecture.kitchenassistant.application.port.in;

import com.example.hexagonalarchitecture.kitchenassistant.domain.Order;

public interface OrderItems {

    Long order(StockRequest request);

    Order findById(Long id);
}
