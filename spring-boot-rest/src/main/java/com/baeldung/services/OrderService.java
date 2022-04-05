package com.baeldung.services;

import java.util.List;

import com.baeldung.persistence.model.Order;

public interface OrderService {

    List<Order> getAllOrdersForCustomer(String customerId);

    Order getOrderByIdForCustomer(String customerId, String orderId);

}
