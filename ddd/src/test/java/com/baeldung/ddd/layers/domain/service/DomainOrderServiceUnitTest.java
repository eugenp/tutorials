package com.baeldung.ddd.layers.domain.service;

import com.baeldung.ddd.layers.domain.Order;
import com.baeldung.ddd.layers.domain.OrderProvider;
import com.baeldung.ddd.layers.domain.Product;
import com.baeldung.ddd.layers.domain.repository.OrderRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DomainOrderServiceUnitTest {

    private OrderRepository orderRepository;
    private DomainOrderService tested;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        tested = new DomainOrderService(orderRepository);
    }

    @Test
    void shouldCreateOrder_thenSaveIt() {
        final Product product = new Product(BigDecimal.TEN, "productName");

        final ObjectId id = tested.createOrder(Arrays.asList(product));

        verify(orderRepository).save(any(Order.class));
        assertNotNull(id);
    }

    @Test
    void shouldAddProduct_thenSaveOrder() {
        final Order order = spy(OrderProvider.getCreatedOrder());
        final Product product = new Product(BigDecimal.TEN, "test");
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        tested.addProduct(order.getId(), product);

        verify(orderRepository).save(order);
        verify(order).addProduct(product);
    }

    @Test
    void shouldAddProduct_thenThrowException() {
        final Product product = new Product(BigDecimal.TEN, "test");
        final ObjectId id = ObjectId.get();
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        final Executable executable = () -> tested.addProduct(id, product);

        verify(orderRepository, times(0)).save(any(Order.class));
        assertThrows(RuntimeException.class, executable);
    }

    @Test
    void shouldCompleteOrder_thenSaveIt() {
        final Order order = spy(OrderProvider.getCreatedOrder());
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        tested.completeOrder(order.getId());

        verify(orderRepository).save(any(Order.class));
        verify(order).complete();
    }

    @Test
    void shouldDeleteProduct_thenSaveOrder() {
        final Order order = spy(OrderProvider.getCreatedOrder());
        final String productName = order
          .getProducts()
          .get(0)
          .getName();
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        tested.deleteProduct(order.getId(), productName);

        verify(orderRepository).save(order);
        verify(order).removeProduct(productName);
    }
}