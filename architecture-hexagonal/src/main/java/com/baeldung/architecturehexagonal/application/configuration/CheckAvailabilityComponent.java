package com.baeldung.architecturehexagonal.application.configuration;

import org.springframework.stereotype.Component;

import com.baeldung.architecturehexagonal.domain.usecases.CheckAvailability;
import com.baeldung.architecturehexagonal.domain.usecases.GetCurrentReservedCapacity;

@Component
public class CheckAvailabilityComponent extends CheckAvailability {
    public CheckAvailabilityComponent(GetCurrentReservedCapacity getCurrentReservedCapacity) {
        super(getCurrentReservedCapacity);
    }
}
