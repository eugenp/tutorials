package com.baeldung.hexagonal;

import static org.junit.Assert.*;

import org.junit.Test;

import com.baeldung.hexagonal.CustomerController;
import com.baeldung.hexagonal.adapters.CustomerServiceAdapter;
import com.baeldung.hexagonal.adapters.InMemoryCustomerRepository;
import com.baeldung.hexagonal.core.Customer;
import com.baeldung.hexagonal.ports.CustomerRepository;
import com.baeldung.hexagonal.ports.CustomerServicePort;

public class CustomerIntegrationTest {

    @Test
    public void shouldRetrieveCustomerWithValidId() {
        CustomerRepository customerRepository = new InMemoryCustomerRepository();
        CustomerServicePort customerService = new CustomerServiceAdapter(customerRepository);
        CustomerController customerController = new CustomerController(customerService);
        Customer john = new Customer(1L, "John");
        assertEquals(john, customerController.getCustomerInfoWith(1L));
    }
}
