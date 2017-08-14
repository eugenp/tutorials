package com.baeldung.dependencyinjection;

public class CustomerServicePlain implements CustomerService {

    private final CustomerRepository repository = new InMemCustomerRepository();
    
    @Override
    public Customer getCustomer(Long customerId) {
        return repository.findById(customerId);
    }
}
