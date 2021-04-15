package com.baeldung.architecture.hexagonal.shopcart.application.ports.in;

import java.util.List;

import com.baeldung.architecture.hexagonal.shopcart.adapter.in.rest.OrderModel;

public interface ListOrdersUseCase {

	List<OrderModel> listAllOrders();
	
}
