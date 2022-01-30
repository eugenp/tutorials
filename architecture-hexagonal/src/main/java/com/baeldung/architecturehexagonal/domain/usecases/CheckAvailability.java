package com.baeldung.architecturehexagonal.domain.usecases;

import com.baeldung.architecturehexagonal.domain.ports.errors.RestaurantNotFound;

public class CheckAvailability {
    private final GetCurrentReservedCapacity getCurrentReservedCapacity;

    public CheckAvailability(GetCurrentReservedCapacity getCurrentReservedCapacity) {
        this.getCurrentReservedCapacity = getCurrentReservedCapacity;
    }

    public Boolean perform(String restaurantName) throws RestaurantNotFound {
        // if reservation increases the reserved capacity beyond 60%, reservation fails
        Integer reservedCapacity = getCurrentReservedCapacity.perform(restaurantName);
        if (reservedCapacity > 60) return Boolean.FALSE;
        return Boolean.TRUE;
    }
}
