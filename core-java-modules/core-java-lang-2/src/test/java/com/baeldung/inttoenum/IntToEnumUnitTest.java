package com.baeldung.inttoenum;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class IntToEnumUnitTest {

        @Test
        public void whenIntToEnumUsingValuesMethod_thenReturnEnumObject() {
                int PIZZA_ORDERED_STATUS_INDEX = 0;
                PizzaStatus[] pizzaOrderStatuses = PizzaStatus.values();
                assertEquals(pizzaOrderStatuses[PIZZA_ORDERED_STATUS_INDEX], PizzaStatus.ORDERED);
        }

        @Test
        public void whenIntToEnumUsingMap_thenReturnEnumObject() {
                int PIZZA_ORDERED_STATUS_INDEX = 0;
                assertEquals(PizzaStatus.castIntToEnum(PIZZA_ORDERED_STATUS_INDEX), PizzaStatus.ORDERED);
        }
}