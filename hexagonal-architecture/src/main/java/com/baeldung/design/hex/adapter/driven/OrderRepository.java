package com.baeldung.design.hex.adapter.driven;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baeldung.design.hex.adapter.driven.component.DataSource;
import com.baeldung.design.hex.business.domain.Item;
import com.baeldung.design.hex.port.out.IOrderRepository;

@Repository
public class OrderRepository implements IOrderRepository {

	@Autowired
	DataSource datasource;

	@Override
	public String placeOrder(List<Item> items) {
		return datasource.placeOrder(items);
	}

	@Override
	public List<Item> getOrderedItems(String orderId) {
		return datasource.getOrderedItems(orderId);
	}

}
