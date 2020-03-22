package com.baeldung.dddhexagonaljava.adapters;

import com.baeldung.dddhexagonaljava.core.domain.Customer;
import com.baeldung.dddhexagonaljava.ports.NotificationService;

public class SmsNotificationService implements NotificationService {

    @Override
    public void sendNotification(Customer customer, String message) {
        //send sms notification
    }
}