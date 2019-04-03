package com.baeldung.hexagonal.core;

import java.util.List;
import java.util.Map;

import com.baeldung.hexagonal.ports.IOrderNotification;
import com.baeldung.hexagonal.ports.IOrderRepository;
import com.baeldung.hexagonal.ports.IOrderService;
import com.google.inject.Inject;

public class OrderService implements IOrderService {
    private IOrderRepository repository;
    private IOrderNotification notification;
     
    @Inject
    public OrderService(IOrderRepository repository, IOrderNotification notification) {
        super();
        this.repository = repository;
        this.notification = notification;
    }

    @Override
    public Order createOrder(Map<String, Integer> orderedItems) throws Exception {
        Order order = new Order();
        
        for(Map.Entry<String, Integer> orderedItem: orderedItems.entrySet()) {
            order.updateItem(repository.findItemById(orderedItem.getKey()), orderedItem.getValue());           
        }
        
        order.setOrderId(OrderUtils.generateOrderId());
        
        
        order = repository.createOrder(order);
        notification.sendNotification(order.getOrderId(), "order is successfully created. Total amount:$"
                +order.getTotalAmount());
        
        return order;
    }

    @Override
    public Order findOrderById(String orderId) throws Exception {   
        
        Order order = repository.findOrderById(orderId);
        notification.sendNotification(orderId, "order is successfully retrieved. There are "
            +order.getOrderedItems().size() + " items in the order."
            +"Total amount:$" + order.getTotalAmount());
        
        return order;
    }

    @Override
    public Order updateOrder(String orderId, Map<String, Integer> orderedItems) 
        throws Exception {
        
        Order order = repository.findOrderById(orderId);
        
        order.clear();
        for(Map.Entry<String, Integer> orderedItem: orderedItems.entrySet()) {
            order.updateItem(repository.findItemById(orderedItem.getKey()), orderedItem.getValue());           
        }
        
        order = repository.updateOrder(order);
        
        notification.sendNotification(order.getOrderId(), "order is successfully updated. Total amount:$" + 
            +order.getTotalAmount());
        
        return order;
    }

    @Override
    public void cancelOrder(String orderId) throws Exception {
        
        repository.cancelOrder(orderId);    
        notification.sendNotification(orderId, "order is successfully cancelled.");
    }

    @Override
    public List<Order> getAllOrders() {
        
        return repository.getAllOrders();
    }


}
