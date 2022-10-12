package com.baeldung.graphqlvsrest.repository;

import com.baeldung.graphqlvsrest.entity.Order;

import java.util.List;

public interface OrderRepository {
    List<Order> getOrdersByProduct(Integer productId);
}
