package com.baeldung.dddhexagonaljava.adapters.service;

import com.baeldung.dddhexagonaljava.core.domain.Customer;
import com.baeldung.dddhexagonaljava.ports.NotificationService;

public class EmailNotificationService implements NotificationService {

    @Override
    public void sendNotification(Customer customer, String message) {
        //send email notification
    }
}