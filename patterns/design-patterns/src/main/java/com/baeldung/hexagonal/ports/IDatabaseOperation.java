package com.baeldung.hexagonal.ports;

import com.baeldung.hexagonal.core.Customer;

public interface IDatabaseOperation {
    
    public boolean AddCustomer(Customer customer);
    
    public Customer findCustomer(int customerID);

    public boolean updateCustomerData(int customerID, Customer customer);

}
