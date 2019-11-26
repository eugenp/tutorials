package com.baeldung.port;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.domain.Order;
import com.baeldung.repo.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService{

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
