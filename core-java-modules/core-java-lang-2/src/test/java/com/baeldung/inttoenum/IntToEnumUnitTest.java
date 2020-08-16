package com.baeldung.inttoenum;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class IntToEnumUnitTest {

    @Test
    public void whenIntToEnumUsingValuesMethod_thenReturnEnumObject() {
        int timeToDeliveryForOrderedPizzaStatus = 5;

        PizzaStatus pizzaOrderedStatus = null;

        for (PizzaStatus pizzaStatus : PizzaStatus.values()) {
            if (pizzaStatus.getTimeToDelivery() == timeToDeliveryForOrderedPizzaStatus) {
                pizzaOrderedStatus = pizzaStatus;
            }
        }

        assertThat(pizzaOrderedStatus).isEqualTo(PizzaStatus.ORDERED);
    }

    @Test
    public void whenIntToEnumUsingMap_thenReturnEnumObject() {
        int timeToDeliveryForOrderedPizzaStatus = 5;

        assertThat(PizzaStatus.castIntToEnum(timeToDeliveryForOrderedPizzaStatus)).isEqualTo(PizzaStatus.ORDERED);
    }

    @Test
    public void whenIntToEnumUsingStream_thenReturnEnumObject() {
        int timeToDeliveryForOrderedPizzaStatus = 5;

        Optional<PizzaStatus> pizzaStatus = Arrays.stream(PizzaStatus.values())
          .filter(p -> p.getTimeToDelivery() == timeToDeliveryForOrderedPizzaStatus)
          .findFirst();

        assertThat(pizzaStatus).hasValue(PizzaStatus.ORDERED);
    }
}