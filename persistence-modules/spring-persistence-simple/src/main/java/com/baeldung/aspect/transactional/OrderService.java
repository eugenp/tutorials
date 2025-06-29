package com.baeldung.aspect.transactional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.aspect.transactional.model.Order;
import com.baeldung.aspect.transactional.repository.OrderRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Transactional //<-- marks this method
    public void createOrder(Order order) {
        orderRepository.save(order);
    }
}
