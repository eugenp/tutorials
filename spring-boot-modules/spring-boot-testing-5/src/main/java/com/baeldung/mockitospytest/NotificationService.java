package com.baeldung.mockitospytest;

import org.springframework.stereotype.Component;

@Component
public class NotificationService {

    private ExternalAlertService externalAlertService;

    public void notify(Order order) {
        System.out.println(order);
    }

    public boolean raiseAlert(Order order) {
        return externalAlertService.alert(order);
    }

}
