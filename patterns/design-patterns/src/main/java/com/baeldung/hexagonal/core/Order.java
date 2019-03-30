package com.baeldung.hexagonal.core;

import java.util.HashMap;
import java.util.Map;

public class Order {
    String orderId;
    
    Map<Item, Integer> orderedItems = new HashMap<>();

    double totalAmount;
    String status;
    
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Map<Item, Integer> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(Map<Item, Integer> orderItems) {
        this.orderedItems = orderItems;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    synchronized public void updateItem(Item item, int count) {
        if(orderedItems == null) {
            orderedItems = new HashMap<>();
        }
        
        if(count ==0) {
            orderedItems.remove(item);
        } else {
            orderedItems.put(item, count);     
        }
        
        //re-calculate the total amount of the order
        this.calculateTotalAmount();
    }
    
    synchronized public void clear() {
        orderedItems.clear();
        totalAmount = 0;
    }
    
    private void calculateTotalAmount() {
        totalAmount = 0;
        for (Map.Entry<Item, Integer> entry: orderedItems.entrySet()) {
            totalAmount +=entry.getKey().getPrice() * entry.getValue();
        }
    }
}
