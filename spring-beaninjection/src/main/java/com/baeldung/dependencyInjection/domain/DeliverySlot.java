package com.baeldung.dependencyInjection.domain;

import org.springframework.stereotype.Component;

@Component
public interface DeliverySlot {

    public boolean deliveryTimeSlotSelected();
}
