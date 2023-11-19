package com.baeldung.graphqlvsrest.repository.impl;

import com.baeldung.graphqlvsrest.entity.Order;
import com.baeldung.graphqlvsrest.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private static final List<Order> ORDER_LIST = new ArrayList<>();

    public OrderRepositoryImpl() {
        for (int i = 1; i <= 100; i++) {
            Order order = new Order();
            order.setId(i);
            order.setProductId(i % 10);
            order.setAddress(i + " A Street");
            order.setCustomerId(UUID.randomUUID().toString());
            order.setCreationDate(new Date(System.currentTimeMillis()).toString());
            order.setStatus("Delivered");
            ORDER_LIST.add(order);
        }
    }

    @Override
    public List<Order> getOrdersByProduct(Integer productId) {
        return ORDER_LIST.stream()
          .filter(order -> order.getProductId().equals(productId))
          .collect(Collectors.toList());
    }
}
