package com.baeldung.inttoenum;

import java.util.HashMap;
import java.util.Map;

public enum PizzaStatus {
        ORDERED,
        READY,
        DELIVERED;

        private static Map intToEnumValuesMapping = new HashMap<>();
        static {
                int ENUM_VAL_INDEX = 0;
                for (PizzaStatus pizzaStatus : PizzaStatus.values()) {
                        intToEnumValuesMapping.put(ENUM_VAL_INDEX, pizzaStatus);
                        ENUM_VAL_INDEX++;
                }
        }

        public static PizzaStatus castIntToEnum(int pageType) {
                return (PizzaStatus) intToEnumValuesMapping.get(pageType);
        }
}