package com.baeldung.hexagonal.domain;

import org.junit.Test;

import static org.mockito.Mockito.*;

public class CustomerTest {

    @Test
    public void whenCustomerNameIsChanged_thenPublishMessage() {
        MessagingService messagingService = mock(MessagingService.class);
        Customer customer = new Customer("id", "John Doe", messagingService);

        Customer changedCustomer = customer.changeName("John Reed");

        verify(messagingService, times(1)).publishMessage(changedCustomer);
    }

}