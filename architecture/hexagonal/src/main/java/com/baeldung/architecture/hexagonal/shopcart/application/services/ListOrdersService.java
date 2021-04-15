package com.baeldung.architecture.hexagonal.shopcart.application.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baeldung.architecture.hexagonal.shopcart.adapter.in.rest.OrderModel;
import com.baeldung.architecture.hexagonal.shopcart.application.ports.in.ListOrdersUseCase;
import com.baeldung.architecture.hexagonal.shopcart.application.ports.out.QueryOrdersPort;
import com.baeldung.architecture.hexagonal.shopcart.domain.Order;

@Service
public class ListOrdersService implements ListOrdersUseCase{
	
	private final QueryOrdersPort queryOrdersPort;
	
	public ListOrdersService(QueryOrdersPort queryOrdersPort) {
		this.queryOrdersPort=queryOrdersPort;
	}
	
	@Override
	public List<OrderModel> listAllOrders() {
		return this.queryOrdersPort.listOrders().stream().map(this::mapToModel).collect(Collectors.toList());
	}
	
	private OrderModel mapToModel(Order order) {
		return new OrderModel(order.getId().toString());
	}

}
