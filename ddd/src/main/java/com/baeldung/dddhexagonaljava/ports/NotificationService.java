package com.baeldung.dddhexagonaljava.ports;

import com.baeldung.dddhexagonaljava.core.domain.Customer;

public interface NotificationService {
    void sendNotification(Customer customer, String message);
}