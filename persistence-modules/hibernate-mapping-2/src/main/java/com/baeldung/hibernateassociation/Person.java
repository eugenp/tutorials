package com.baeldung.hibernateassociation;

import java.util.List;

public class Order {
    private int orderId;
    private List<Item> items;
    
    public Order(int orderId, List<Item> items) {
        this.orderId = orderId;
        this.items = items;
    }
    
    public int getOrderId() {
        return orderId;
    }
    
    public List<Item> getItems() {
        return items;
    }
}

public class Item {
    private int itemId;
    private Order order;
    
    public Item(int itemId) {
        this.itemId = itemId;
    }
    
    public int getItemId() {
        return itemId;
    }
    
    public void setOrder(Order order) {
        this.order = order;
    }
    
    public Order getOrder() {
        return order;
    }
}
