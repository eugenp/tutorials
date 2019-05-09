package com.baeldung.hexagonal.secondary;


import com.baeldung.hexagonal.core.Customer;


public class CustomerRepositoryProductionAdapter implements CustomerRepositoryPort {
    public void saveCustomer(Customer customer) {

    }

    public void deleteCustomer(int customerId) {

    }

    public Customer findCustomer(int customerId) {
        return new Customer();
    }

    public void updateCustomer(Customer customer) {

    }
}
