package com.baeldung.typesbeaninjection.setterbased;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class CustomerService {

    // The CustomerRepository has a dependency on the CustomerService
    private CustomerRepository customerRepository;

    // A setter method so that the Spring container can inject a CustomerRepository
    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // below some business logic that can use the injected CustomerRepository
    @PostConstruct
    public void init() {
        System.out.println(customerRepository.findOne());
    }
}
