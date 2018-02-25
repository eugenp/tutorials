package com.baeldung.ditypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SmsComponentWithAck {

    private final SmsService smsService;
    private DeliveryService deliveryService;

    @Autowired
    public SmsComponentWithAck(SmsService smsService) {
        this.smsService = smsService;
    }

    @Autowired
    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }
}
