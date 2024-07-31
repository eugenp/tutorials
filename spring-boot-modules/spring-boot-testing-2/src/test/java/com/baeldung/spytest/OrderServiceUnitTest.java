package com.baeldung.spytest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class OrderServiceUnitTest {

    @Spy
    OrderRepository orderRepository;
    @Spy
    NotificationService notificationService;
    @InjectMocks
    OrderService orderService;

    @Test
    void givenNotificationServiceIsUsingSpy_whenOrderServiceIsCalled_thenNotificationServiceSpyShouldBeInvoked() {

        UUID orderId = UUID.randomUUID();
        Order orderInput = new Order(orderId, "Test", 1.0, "17 St Andrews Croft, Leeds ,LS17 7TP");
        doReturn(orderInput).when(orderRepository)
            .save(any());
        doReturn(true).when(notificationService)
            .raiseAlert(any(Order.class));
        Order order = orderService.save(orderInput);
        Assertions.assertNotNull(order);
        Assertions.assertEquals(orderId, order.getId());
        verify(notificationService).notify(any(Order.class));
    }

}