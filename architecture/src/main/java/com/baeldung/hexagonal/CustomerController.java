package com.baeldung.hexagonal;

import com.baeldung.hexagonal.core.Customer;
import com.baeldung.hexagonal.ports.CustomerServicePort;

public class CustomerController {

    private CustomerServicePort customerServicePort;
    
    public CustomerController(CustomerServicePort customerServicePort) {
        this.customerServicePort = customerServicePort;
    }
    
    public Customer getCustomerInfoWith(Long id) {
        return this.customerServicePort.findCustomerBy(id);
    }
}
