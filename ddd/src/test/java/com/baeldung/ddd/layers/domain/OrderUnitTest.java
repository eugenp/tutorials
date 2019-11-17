package com.baeldung.ddd.layers.domain;

import com.baeldung.ddd.layers.domain.exception.DomainException;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.util.Arrays;

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
        final int orderOriginalProductSize = order.getProducts().size();
        final BigDecimal orderOriginalPrice = order.getPrice();
        final Product productToAdd = new Product(new BigDecimal("20"), "secondProduct");

        order.addProduct(productToAdd);

        assertEquals(orderOriginalProductSize + 1, order.getProducts().size());
        assertEquals(orderOriginalPrice.add(productToAdd.getPrice()), order.getPrice());
    }

    @Test
    void shouldAddProduct_thenThrowException(){
        final Order order = OrderProvider.getCompletedOrder();
        final Product productToAdd = new Product(new BigDecimal("20"), "secondProduct");

        final Executable executable = () -> order.addProduct(productToAdd);

        Assertions.assertThrows(DomainException.class, executable);
    }

    @Test
    void shouldRemoveProduct_thenUpdatePrice() {
        final Order order = OrderProvider.getCreatedOrder();

        order.removeProduct(order.getProducts().get(0).getName());

        assertEquals(0, order.getProducts().size());
        assertEquals(BigDecimal.ZERO, order.getPrice());
    }
}