package com.baeldung.inttoenum;

import java.util.HashMap;
import java.util.Map;

public enum PizzaStatus {
        ORDERED,
        READY,
        DELIVERED;

        private static Map intToEnumValuesMapping = new HashMap<>();
        static {
                int PIZZA_ORDER_STATUS_INDEX = 0;
                for (PizzaStatus pizzaStatus : PizzaStatus.values()) {
                        intToEnumValuesMapping.put(PIZZA_ORDER_STATUS_INDEX, pizzaStatus);
                        PIZZA_ORDER_STATUS_INDEX++;
                }
        }

        public static PizzaStatus castIntToEnum(int pizzaOrderStatusIndex) {
                return (PizzaStatus) intToEnumValuesMapping.get(pizzaOrderStatusIndex);
        }
}