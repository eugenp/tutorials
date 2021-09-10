package com.example.hexagonalarchitecture.kitchenassistant.application.port.in;

import com.example.hexagonalarchitecture.kitchenassistant.application.port.in.dtos.StockRequest;
import com.example.hexagonalarchitecture.kitchenassistant.domain.Order;

public interface OrderItemsPort {

    Long order(StockRequest request);

    Order findById(Long id);
}
