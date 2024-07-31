package com.baeldung.dddhexagonalspring.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderUnitTest {

    @Test
    void shouldCompleteOrder_thenChangeStatus() {
        final Order order = OrderProvider.getCreatedOrder();

        order.complete();

        assertEquals(OrderStatus.COMPLETED, order.getStatus());
    }

    @Test
    void shouldAddProduct_thenUpdatePrice() {
        final Order order = OrderProvider.getCreatedOrder();
        final int orderOriginalProductSize = order
          .getOrderItems()
          .size();
        final BigDecimal orderOriginalPrice = order.getPrice();
        final Product productToAdd = new Product(UUID.randomUUID(), new BigDecimal("20"), "secondProduct");

        order.addOrder(productToAdd);

        assertEquals(orderOriginalProductSize + 1, order
          .getOrderItems()
          .size());
        assertEquals(orderOriginalPrice.add(productToAdd.getPrice()), order.getPrice());
    }

    @Test
    void shouldAddProduct_thenThrowException() {
        final Order order = OrderProvider.getCompletedOrder();
        final Product productToAdd = new Product(UUID.randomUUID(), new BigDecimal("20"), "secondProduct");

        final Executable executable = () -> order.addOrder(productToAdd);

        Assertions.assertThrows(DomainException.class, executable);
    }

    @Test
    void shouldRemoveProduct_thenUpdatePrice() {
        final Order order = OrderProvider.getCreatedOrder();
        final UUID productId = order
          .getOrderItems()
          .get(0)
          .getProductId();
        
        order.removeOrder(productId);

        assertEquals(0, order
          .getOrderItems()
          .size());
        assertEquals(BigDecimal.ZERO, order.getPrice());
    }
}