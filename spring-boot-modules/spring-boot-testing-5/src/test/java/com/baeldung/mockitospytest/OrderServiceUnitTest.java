package com.baeldung.mockitospytest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@SpringBootTest
class OrderServiceUnitTest {
    @MockitoSpyBean
    NotificationService notificationService;
    @MockitoSpyBean
    OrderService orderService;

    @Test
    void givenNotificationServiceIsUsingSpyBean_whenOrderServiceIsCalled_thenNotificationServiceSpyBeanShouldBeInvoked() {
        Order orderInput = new Order(null, "Test", 1.0, "17 St Andrews Croft, Leeds ,LS17 7TP");
        doReturn(true).when(notificationService)
            .raiseAlert(any(Order.class));
        Order order = orderService.save(orderInput);
        Assertions.assertNotNull(order);
        Assertions.assertNotNull(order.getId());
        verify(notificationService).notify(any(Order.class));
    }

}