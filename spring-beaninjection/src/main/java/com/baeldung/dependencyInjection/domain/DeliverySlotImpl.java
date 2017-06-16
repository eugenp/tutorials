package com.baeldung.dependencyInjection.domain;

import org.springframework.stereotype.Component;

@Component
public class DeliverySlotImpl implements DeliverySlot {

    public boolean deliveryTimeSlotSelected() {
        return true;
    }
}
