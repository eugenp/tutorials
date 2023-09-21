package com.baeldung.spytest;

import org.springframework.stereotype.Component;

@Component
public class NotificationService {
    public void notify(Order order){
        System.out.println(order);
    }

}
