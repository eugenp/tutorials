package com.baeldung.domain;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.spi.OrderRepository;

@Service
public class OrderDomainServiceImpl implements OrderDomainService{

    @Autowired
    private OrderRepository orderRepository;
    
    public void createOrder(Long amount) {
        orderRepository.save(new Order(amount));
    }
    
    public Order getOrder(Integer id) {
        Optional<Order> order = orderRepository.findById(id);
        if(order.isPresent())
            return order.get();
        return null;
    }
}
