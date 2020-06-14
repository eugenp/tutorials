package com.baeldung.hexagonal.domain.service;

import com.baeldung.hexagonal.domain.Order;
import com.baeldung.hexagonal.domain.repository.OrderRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.data.Percentage.withPercentage;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceUnitTest {

    private OrderRepository orderRepository;

    private DomainOrderService orderService;

    public OrderServiceUnitTest() {
        this.orderRepository = mock(OrderRepository.class);
        this.orderService = new DomainOrderService();
        this.orderService.setOrderRepository(this.orderRepository);
    }

    @Test
    void shouldFindOrder() {
        when(orderRepository.findById(1L)).thenReturn(new Order(1L, new BigDecimal(1000)));
        Order order = this.orderService.findById(1L);
        assertThat(order.getPrice()).isCloseTo(new BigDecimal(1000), withPercentage(0.0001));

    }
}
