package com.baeldung.hexagonal.infra.sms;

import com.baeldung.hexagonal.domain.Customer;
import com.baeldung.hexagonal.domain.NotificationService;

public class SmsNotificationService implements NotificationService {

    @Override
    public void notifyCustomerChanged(Customer customer) {
        // sms notification implementation
    }

}
