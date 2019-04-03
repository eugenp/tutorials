package com.baeldung.hexagonal.ports;

import java.util.List;
import java.util.Map;

import com.baeldung.hexagonal.core.Order;

public interface IOrderService {
    public Order createOrder(Map<String, Integer> orderedItems) throws Exception;
    
    public Order findOrderById(String orderId) throws Exception;
    
    public Order updateOrder(String orderId, Map<String, Integer> orderedItems) 
        throws Exception;
    
    public void cancelOrder(String orderId) throws Exception;
    
    public List<Order> getAllOrders();
}
