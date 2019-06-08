package com.baeldung.controller.api;

import com.baeldung.domain.entity.Customer;
import com.baeldung.domain.port.CustomerServicePort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private CustomerServicePort customerServicePort;

    public CustomerController(CustomerServicePort customerServicePort) {
        this.customerServicePort = customerServicePort;
    }

    @GetMapping()
    public Customer getCustomer() {
        return customerServicePort.getCustomer();
    }
}
