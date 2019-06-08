package com.baeldung.domain.services;

import com.baeldung.domain.entity.Customer;
import com.baeldung.domain.port.CustomerPort;

public class CustomerService {
    private CustomerPort customerPort;

    public CustomerService(CustomerPort customerPort) {
        this.customerPort = customerPort;
    }

    public Customer getRandomCustomer(long id) {
        return customerPort.getCustomerById(id);
    }
}
