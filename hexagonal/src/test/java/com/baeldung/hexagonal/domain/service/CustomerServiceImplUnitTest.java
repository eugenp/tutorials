package com.baeldung.hexagonal.domain.service;

import com.baeldung.hexagonal.domain.model.Customer;
import com.baeldung.hexagonal.port.in.CustomerService;
import com.baeldung.hexagonal.port.out.CustomerPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CustomerServiceImplUnitTest {

    private CustomerService customerService;
    private CustomerPersistencePort customerPersistencePort;

    @BeforeEach
    void setup() {
        customerPersistencePort = mock(CustomerPersistencePort.class);
        customerService = new CustomerServiceImpl(customerPersistencePort);
    }

    @Test
    public void whenGetByUsername_thenShouldCallPersistencePortGetByUsername() {
        final String username = "user1";
        customerService.getByUsername(username);

        verify(customerPersistencePort, times(1)).getByUsername(username);
    }

    @Test
    public void whenRegister_thenShouldCallPersistencePortSave() {
        Customer customer = Customer.builder()
                .username("user1")
                .password("pass1")
                .deposit(100L)
                .build();
        customerService.register(customer);

        verify(customerPersistencePort, times(1)).save(customer);
    }
}