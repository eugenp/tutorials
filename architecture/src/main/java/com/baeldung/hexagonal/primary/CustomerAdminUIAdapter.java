package com.baeldung.hexagonal.primary;


import com.baeldung.hexagonal.core.Customer;
import com.baeldung.hexagonal.core.CustomerService;

public class CustomerAdminUIAdapter implements CustomerAdminUIPort {
    private CustomerService customerService;

    public CustomerAdminUIAdapter(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void addCustomer(Customer customer) {
        customerService.addCustomer(customer);
    }

    public void deleteCustomer(int customerId) {
        customerService.deleteCustomer(customerId);
    }

    public Customer getCustomer(int customerId) {
        return customerService.getCustomer(customerId);
    }

    public void editCustomer(Customer customer) {
        customerService.editCustomer(customer);
    }
}
