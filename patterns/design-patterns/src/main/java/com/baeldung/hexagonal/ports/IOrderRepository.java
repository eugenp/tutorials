package com.baeldung.hexagonal.ports;

import java.util.List;

import com.baeldung.hexagonal.core.Item;
import com.baeldung.hexagonal.core.Order;

public interface IOrderRepository {
    public Order createOrder(Order order);
    public Order findOrderById(String orderId) throws Exception;
    public Order updateOrder(Order order) throws Exception;
    public void cancelOrder(String orderId) throws Exception;
    
    public List<Order> getAllOrders();
    
    public Item findItemById(String itemId) throws Exception;
}
