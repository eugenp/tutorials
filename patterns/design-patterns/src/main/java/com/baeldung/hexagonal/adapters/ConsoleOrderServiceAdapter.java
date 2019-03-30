package com.baeldung.hexagonal.adapters;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.baeldung.hexagonal.core.Order;
import com.baeldung.hexagonal.exception.ItemNotFoundException;
import com.baeldung.hexagonal.exception.OrderNotFoundException;
import com.baeldung.hexagonal.ports.IOrderService;

public class ConsoleOrderServiceAdapter {
    private Scanner scanner;
    private IOrderService orderService;
    
    public ConsoleOrderServiceAdapter(Scanner scanner, IOrderService orderService) {
        super();
        this.scanner = scanner;
        this.orderService = orderService;
    }
    
    public String createOrder() throws ItemNotFoundException {
        System.out.println("Enter the Order Items in 'name:count' pair, separate by comma, as 'item1:1,item2:2'");
        System.out.print(">");
        String itemsStr = scanner.next();
        String[] itemsParts = itemsStr.split(",");
        
        Map<String, Integer> orderedItems = new HashMap<>();
        for(String itemStr: itemsParts) {
            String[] itemParts = itemStr.split(":");
            Integer count = Integer.parseInt(itemParts[1]);
            
            orderedItems.put(itemParts[0], count);
        }
        
        Order order = orderService.createOrder(orderedItems);
        
        return order.getOrderId();
    }
    
    public Order findOrderById() throws OrderNotFoundException {
        System.out.println("Enter the Order id:");
        System.out.print(">");
        String orderId = scanner.next();
        
        Order order = orderService.findOrderById(orderId);
     
        return order;
    }

    public void cancelOrder() throws OrderNotFoundException {
        System.out.println("Enter the Order id:");
        System.out.print(">");
        String orderId = scanner.next();
        
        orderService.cancelOrder(orderId);
    }

    public String updateOrder() throws ItemNotFoundException, OrderNotFoundException {
        System.out.println("Enter the Order Items in 'orderId--name:count' pair, separate by comma, as '827abbe5-2cd1-4426-b56a-81caf18549be-item1:1,item2:2'");
        System.out.print(">");
        String orderStr = scanner.next();
        
        String[] orderParts = orderStr.split("--");
        String orderId = orderParts[0];
        String itemsStr = orderParts[1];
        String[] itemsParts = itemsStr.split(",");
        
        Map<String, Integer> orderedItems = new HashMap<>();
        for(String itemStr: itemsParts) {
            String[] itemParts = itemStr.split(":");
            Integer count = Integer.parseInt(itemParts[1]);
            
            orderedItems.put(itemParts[0], count);
        }
        
        Order order = orderService.updateOrder(orderId, orderedItems);
        
        return order.getOrderId();
    }
}
