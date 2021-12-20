package com.baeldung.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CoffeeOrderUnitTest {

    @Test
    void givenStatusIsPending_whenStatusUpdate_thenUpdateTrue() {
        CoffeeOrder order = new CoffeeOrder()
          .setStatus(OrderStatus.PENDING);

        boolean updateResult = order.updateStatus(OrderStatus.COMPLETE);

        Assertions.assertTrue(updateResult);
    }

    @Test
    void givenStatusIsNotPending_whenStatusUpdate_thenUpdateFalse() {
        CoffeeOrder order = new CoffeeOrder()
          .setStatus(OrderStatus.CANCELED);

        boolean updateResult = order.updateStatus(OrderStatus.COMPLETE);

        Assertions.assertFalse(updateResult);
    }
}
