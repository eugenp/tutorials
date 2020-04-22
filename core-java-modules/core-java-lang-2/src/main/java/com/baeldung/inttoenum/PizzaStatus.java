package com.baeldung.inttoenum;

import java.util.HashMap;
import java.util.Map;

public enum PizzaStatus {
        ORDERED,
        READY,
        DELIVERED;

        private static Map<Integer, PizzaStatus> intToEnumValuesMapping = new HashMap<>();
        static {
                PizzaStatus[] pizzaStatuses = PizzaStatus.values();
                for(int pizzaStatusIndex = 0; pizzaStatusIndex < pizzaStatuses.length; pizzaStatusIndex++) {
                        intToEnumValuesMapping.put(pizzaStatusIndex, pizzaStatuses[pizzaStatusIndex]);
                }
        }

        public static PizzaStatus castIntToEnum(int pizzaStatusIndex) {
                return intToEnumValuesMapping.get(pizzaStatusIndex);
        }
}