package com.baeldung.hexagonal.primary;


import com.baeldung.hexagonal.core.Customer;


    public interface CustomerAdminUIPort {
        void addCustomer(Customer customer);
        void deleteCustomer(int customerId);
        Customer getCustomer(int customerId);
        void editCustomer(Customer customer);
    }
