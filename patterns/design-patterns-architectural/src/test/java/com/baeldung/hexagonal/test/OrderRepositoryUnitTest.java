package com.baeldung.hexagonal.test;

import com.baeldung.hexagonal.domain.Order;
import com.baeldung.hexagonal.domain.OrderRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;

public class OrderRepositoryUnitTest {

    private static OrderRepository orderRepository;

    @BeforeClass
    public static void setUpOrderRepositoryInstance() {
        orderRepository = new OrderRepository();
    }

    @Test
    public void givenOrderRepositoryInstance_whenOrderCreated_thenOrderClassReturned() {
        assertThat(orderRepository.createOrder("1234")).isInstanceOf(Order.class);
    }
}