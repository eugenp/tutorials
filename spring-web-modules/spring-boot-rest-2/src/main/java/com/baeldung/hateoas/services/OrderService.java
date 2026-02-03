package com.baeldung.hateoas.services;

import java.util.List;

import com.baeldung.hateoas.persistence.model.Order;

public interface OrderService {

    List<Order> getAllOrdersForCustomer(String customerId);

    Order getOrderByIdForCustomer(String customerId, String orderId);

}
