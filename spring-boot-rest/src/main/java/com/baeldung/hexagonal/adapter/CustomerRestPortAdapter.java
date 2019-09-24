package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.domain.Customer;
import com.baeldung.hexagonal.port.CustomerRestPort;
import com.baeldung.hexagonal.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerRestPortAdapter implements CustomerRestPort {

    @Autowired
    private CustomerService customerService;

    @Override
    public void create(Customer customerRequest) {
        customerService.create(customerRequest.getName(), customerRequest.getCompanyName());
    }

    @Override
    public Customer find(String name) {
        return customerService.find(name);
    }
}
