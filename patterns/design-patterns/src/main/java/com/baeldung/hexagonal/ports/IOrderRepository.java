package com.baeldung.hexagonal.ports;

import java.util.List;

import com.baeldung.hexagonal.core.Item;
import com.baeldung.hexagonal.core.Order;
import com.baeldung.hexagonal.exception.ItemNotFoundException;
import com.baeldung.hexagonal.exception.OrderNotFoundException;

public interface IOrderRepository {
    public Order createOrder(Order order);
    public Order findOrderById(String orderId) throws OrderNotFoundException;
    public Order updateOrder(Order order) throws OrderNotFoundException;
    public void cancelOrder(String orderId) throws OrderNotFoundException;
    
    public List<Order> getAllOrders();
    
    public Item findItemById(String itemId) throws ItemNotFoundException;
}
