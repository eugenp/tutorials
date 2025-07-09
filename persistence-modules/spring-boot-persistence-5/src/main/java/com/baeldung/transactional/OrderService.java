package com.baeldung.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private TestOrderRepository orderRepository;

    @Transactional //<-- marks this method
    public void createOrder(TestOrder order) {
        orderRepository.save(order);
    }
}
