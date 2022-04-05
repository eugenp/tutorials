package com.baeldung.inttoenum;

import java.util.HashMap;
import java.util.Map;

public enum PizzaStatus {
    ORDERED(5),
    READY(2),
    DELIVERED(0);

    private int timeToDelivery;

    PizzaStatus(int timeToDelivery) {
        this.timeToDelivery = timeToDelivery;
    }

    public int getTimeToDelivery() {
        return timeToDelivery;
    }

    private static Map<Integer, PizzaStatus> timeToDeliveryToEnumValuesMapping = new HashMap<>();

    static {
        for (PizzaStatus pizzaStatus : PizzaStatus.values()) {
            timeToDeliveryToEnumValuesMapping.put(
                    pizzaStatus.getTimeToDelivery(),
                    pizzaStatus
            );
        }
    }

    public static PizzaStatus castIntToEnum(int timeToDelivery) {
        return timeToDeliveryToEnumValuesMapping.get(timeToDelivery);
    }
}