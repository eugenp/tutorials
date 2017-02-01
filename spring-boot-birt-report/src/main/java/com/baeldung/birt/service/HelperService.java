package com.baeldung.birt.service;

import com.baeldung.birt.domain.Customer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by dsharew on 1/14/17.
 */
@Service
public class HelperService {

    private final CustomerService customerService;

    public HelperService(CustomerService customerService) {

        this.customerService = customerService;

    }

    @PostConstruct
    public void startUp() {

        for (int i = 0; i < 100; ++i) {

            Customer customer = new Customer();

            customer.setFirstName("Jhon " + i);
            customer.setLastName("Smith " + i);
            customer.setAge((int) (Math.random() * 100));
            customer.setBalance((int) (Math.random() * 10000));

            customerService.createCustomer(customer);

        }

    }

}
