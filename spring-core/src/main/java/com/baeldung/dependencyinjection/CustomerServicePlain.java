package com.baeldung.dependencyinjection;

import java.util.List;

public class CustomerServicePlain implements CustomerService {

    private final CustomerRepository repository = new InMemCustomerRepository();
    
    @Override
    public List<Customer> getAll() {
        return repository.findAll();
    }

}
