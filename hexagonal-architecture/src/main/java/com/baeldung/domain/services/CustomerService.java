package com.baeldung.domain.services;

import com.baeldung.domain.entity.Customer;
import com.baeldung.domain.port.CustomerRepositoryPort;
import com.baeldung.domain.port.CustomerServicePort;

public class CustomerService implements CustomerServicePort {
    private CustomerRepositoryPort customerRepositoryPort;

    public CustomerService(CustomerRepositoryPort customerRepositoryPort) {
        this.customerRepositoryPort = customerRepositoryPort;
    }

    @Override
    public Customer getCustomer() {
        return customerRepositoryPort.getCustomer();
    }
}
