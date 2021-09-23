package com.baeldung.design.hex.business.service;

import java.util.List;

import com.baeldung.design.hex.business.domain.Item;

public interface IOrderService {
    public String processOrder(List<Item> items, String caller);

    public List<Item> getOrderedItems(String orderId);
}
