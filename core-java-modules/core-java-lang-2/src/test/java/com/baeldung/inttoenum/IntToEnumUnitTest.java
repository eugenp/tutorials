package com.baeldung.inttoenum;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class IntToEnumUnitTest {

        @Test
        public void whenIntToEnumUsingValuesMethod_thenReturnEnumObject() {
                int ENUM_VAL_ONE_INDEX = 0;
                PizzaStatus[] pizzaStatuses = PizzaStatus.values();
                assertEquals(pizzaStatuses[ENUM_VAL_ONE_INDEX], PizzaStatus.ORDERED);
        }

        @Test
        public void whenIntToEnumUsingMap_thenReturnEnumObject() {
                int ENUM_VAL_ONE_INDEX = 0;
                assertEquals(PizzaStatus.castIntToEnum(ENUM_VAL_ONE_INDEX), PizzaStatus.ORDERED);
        }
}