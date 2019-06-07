package com.baeldung.domain.services;

import com.baeldung.domain.entity.Customer;
import com.baeldung.domain.port.CustomerPort;

import java.util.Random;

public class CustomerService {
    private CustomerPort customerPort;

    public CustomerService(CustomerPort customerPort) {
        this.customerPort = customerPort;
    }

    public Customer getRandomCustomer() {
        return customerPort.getCustomerById(newRandomFrom1To10());
    }

    private long newRandomFrom1To10() {
        return new Random()
                .ints(1, 10)
                .findFirst()
                .getAsInt();
    }
}
