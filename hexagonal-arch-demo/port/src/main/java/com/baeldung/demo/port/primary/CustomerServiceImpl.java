package com.baeldung.demo.port.primary;

import com.baeldung.demo.customer.jpaadapter.dao.CustomerPersistenceServiceImpl;
import com.baeldung.demo.model.Customer;
import com.baeldung.demo.port.secondary.CustomerPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerPersistenceService customerPersistenceService = new CustomerPersistenceServiceImpl();
    public Customer createCustomer(Customer customer) {
        return customerPersistenceService.createCustomer(customer);
    }

    public Customer updateCustomerAddress(Customer customer) {
        return customerPersistenceService.updateCustomer(customer);
    }

    public boolean isFreeShippingEligible(String customerId) {
        Customer customer = customerPersistenceService.retrieveCustomer(customerId);
        return customer.isFreeShippingEligible();
    }

    public Customer getCustomer(String customerId) {
        return customerPersistenceService.retrieveCustomer(customerId);
    }

}

