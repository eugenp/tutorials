package com.baeldung.hexagonalarchitecture;

import com.baeldung.hexagonalarchitecture.adapter.EmailService;
import com.baeldung.hexagonalarchitecture.domain.Customer;
import com.baeldung.hexagonalarchitecture.domain.Order;
import com.baeldung.hexagonalarchitecture.domain.OrderStatus;
import org.junit.Test;
import org.mockito.Mockito;

public class OrderUnitTest {

    @Test
    public void whenOrderStatusChanges_thenInvokePortMethod() {
        EmailService emailService = Mockito.mock(EmailService.class);
        Customer customer = new Customer("0001", "Baeldung", "team@baeldung.com");

        Order order = new Order.Builder()
                .withId("0001")
                .withCustomer(customer)
                .withStatus(OrderStatus.PLACED)
                .withOrderStatusChangeNotifier(emailService)
                .build();

        order.changeStatus(OrderStatus.IN_PROGRESS);

        Mockito.verify(emailService).orderStatusChanged(order);

    }

}
