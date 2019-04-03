package com.baeldung.hexagonal.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baeldung.hexagonal.core.Item;
import com.baeldung.hexagonal.core.Order;
import com.baeldung.hexagonal.ports.IOrderRepository;

public class InMemoryOrderRepositoryAdapter implements IOrderRepository {
    static Map<String, Item> allItems = new HashMap<>();
    static {
        initializeAllItems();
    }
    
    static Map<String, Order> allOrders = Collections.synchronizedMap(new HashMap<>());
    
    static void initializeAllItems() {
        Item item1 = new Item("item1","item1","item 1", 1.01);
        Item item2 = new Item("item2","item2","item 2", 2.02);
        Item item3 = new Item("item3","item3","item 3", 3.03);
        Item item4 = new Item("item4","item4","item 4", 4.04);
        Item item5 = new Item("item5","item5","item 5", 5.05);
        
        allItems.put(item1.getItemId(), item1);
        allItems.put(item2.getItemId(), item2);
        allItems.put(item3.getItemId(), item3);
        allItems.put(item4.getItemId(), item4);
        allItems.put(item5.getItemId(), item5);
    }
    
    @Override
    public Order createOrder(Order order) {
        allOrders.put(order.getOrderId(), order);
        
        return order;
    }

    @Override
    public Order findOrderById(String orderId) throws Exception {
        if(allOrders.containsKey(orderId)) {
            return allOrders.get(orderId);
        }
        
        throw new Exception("Order ("+orderId+") isn't found.");
    }

    @Override
    public Order updateOrder(Order order) throws Exception {
        this.findOrderById(order.getOrderId());
        
        allOrders.put(order.getOrderId(), order);
        
        return order;
    }

    @Override
    public void cancelOrder(String orderId) throws Exception {
        this.findOrderById(orderId);

        allOrders.remove(orderId);
    }

    @Override
    public List<Order> getAllOrders() {
        return new ArrayList<Order>(allOrders.values());
    }


    @Override
    public Item findItemById(String itemId) throws Exception {
        if(allItems.containsKey(itemId)) {
            return allItems.get(itemId);
        }
        
        throw new Exception("Item ("+itemId+") isn't found.");
    }

}
