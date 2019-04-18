package com.baeldung.hexagonal.ports;

import java.util.List;
import java.util.Map;

import com.baeldung.hexagonal.core.Order;
import com.baeldung.hexagonal.exception.ItemNotFoundException;
import com.baeldung.hexagonal.exception.OrderNotFoundException;

public interface IOrderService {
    public Order createOrder(Map<String, Integer> orderedItems) throws ItemNotFoundException;
    
    public Order findOrderById(String orderId) throws OrderNotFoundException;
    
    public Order updateOrder(String orderId, Map<String, Integer> orderedItems) 
        throws OrderNotFoundException, ItemNotFoundException;
    
    public void cancelOrder(String orderId) throws OrderNotFoundException;
    
    public List<Order> getAllOrders();
}
