package com.baeldung.hexagonal.domain;

import org.junit.Test;

import static org.mockito.Mockito.*;

public class CustomerUnitTest {

    @Test
    public void whenCustomerNameIsChanged_thenNotify() {
        NotificationService notificationService = mock(NotificationService.class);
        Customer customer = new Customer("id", "John Doe", notificationService);

        Customer changedCustomer = customer.changeName("John Reed");

        verify(notificationService, times(1)).notifyCustomerChanged(changedCustomer);
    }

}