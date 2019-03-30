package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.ports.IOrderNotification;

public class ConsoleOrderNotification implements IOrderNotification {

    @Override
    public void sendNotification(String orderId, String message) {
        System.out.println("Order Id:"+orderId+" msg:"+message);        
    }
}
