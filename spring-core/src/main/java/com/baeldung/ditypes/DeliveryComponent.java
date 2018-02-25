package com.baeldung.ditypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeliveryComponent {

    private SmsService smsService;

    @Autowired
    private void setSmsService(SmsService smsService) {
        this.smsService = smsService;
    }
}
