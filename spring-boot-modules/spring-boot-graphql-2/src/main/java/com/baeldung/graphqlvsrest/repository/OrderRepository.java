package com.baeldung.graphqlvsrest.repository;

import java.util.List;

import com.baeldung.graphqlvsrest.entity.Order;

public interface OrderRepository {
    List<Order> getOrdersByProduct(Integer productId);
}
