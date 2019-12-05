package com.baeldung.hexagonal.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.boundary.input.OrderService;
import com.baeldung.hexagonal.boundary.output.OrderRepository;
import com.baeldung.hexagonal.core.entities.Order;

@Service public class OrderServiceImpl implements OrderService {

	@Autowired OrderRepository orderRepository;
	
	@Override
	public Iterable<Order> getOrders() {
		return orderRepository.findAll();
	}

	@Override
	public boolean registerOrder(Order order) {
		if (order.getTotal() > 0)
			orderRepository.save(order);
		else
			return false;
		
		return true;
	}
	
	@Override
	public Order createOrder(Double total) {
		Order order = new Order();
		order.setTotal(total);	
		return order;
	}
}
