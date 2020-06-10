package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.domain.CustomerType;
import com.baeldung.hexagonal.domain.Order;
import com.baeldung.hexagonal.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DomainOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private static final Double VAT = 6.5D;

    @Autowired
    DomainOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order applyVAT(Order order) {
        if(order.getId() == null) {
            order.setId(UUID.randomUUID());
        }

        if (order.getCustomerType() != CustomerType.EXEMPT) {
            order.setVat(VAT);
        } else {
            order.setVat(0d);
        }
        orderRepository.save(order);
        return order;
    }

}
