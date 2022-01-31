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

    private static List<Order> orderList = new ArrayList<>();

    public OrderRepositoryImpl() {
        for (int i = 1; i <= 100; i++){
            Order order = new Order();
            order.setId(i);
            order.setProduct_id(i%10);
            order.setAddress(UUID.randomUUID().toString());
            order.setCustomer_uuid(UUID.randomUUID().toString());
            order.setCreation_date(new Date(System.currentTimeMillis()).toString());
            order.setStatus("Delivered");
            orderList.add(order);
        }
    }


    @Override
    public List<Order> getOrdersByProduct(Integer productId) {
        return orderList.stream().filter(order -> order.getProduct_id().equals(productId)).collect(Collectors.toList());
    }
}
