package com.baeldung.hexagonal.secondary;


import com.baeldung.hexagonal.core.Customer;


public interface CustomerRepositoryPort {
    void saveCustomer(Customer customer);
    void deleteCustomer(int customerId);
    Customer findCustomer(int customerId);
    void updateCustomer(Customer customer);
}
