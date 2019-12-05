package com.baeldung.hexagonal.boundary.input;

import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.core.entities.Order;

@Service public interface OrderService {
	Iterable<Order> getOrders();
	Order createOrder(Double total);
	boolean registerOrder(Order order);
}
