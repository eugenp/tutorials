package com.baeldung.inttoenum;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IntToEnumUnitTest {

    @Test
    public void whenIntToEnumUsingValuesMethod_thenReturnEnumObject() {
        int timeToDeliveryForOrderedPizzaStatus = 5;
        PizzaStatus[] pizzaStatuses = PizzaStatus.values();
        PizzaStatus pizzaOrderedStatus = null;
        for (int pizzaStatusIndex = 0; pizzaStatusIndex < pizzaStatuses.length; pizzaStatusIndex++) {
            if (pizzaStatuses[pizzaStatusIndex].getTimeToDelivery() == timeToDeliveryForOrderedPizzaStatus) {
                pizzaOrderedStatus = pizzaStatuses[pizzaStatusIndex];
            }
        }
        assertEquals(pizzaOrderedStatus, PizzaStatus.ORDERED);
    }

    @Test
    public void whenIntToEnumUsingMap_thenReturnEnumObject() {
        int timeToDeliveryForOrderedPizzaStatus = 5;
        assertEquals(PizzaStatus.castIntToEnum(timeToDeliveryForOrderedPizzaStatus), PizzaStatus.ORDERED);
    }
}