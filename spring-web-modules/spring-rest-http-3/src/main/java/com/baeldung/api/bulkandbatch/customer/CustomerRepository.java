package com.baeldung.api.bulkandbatch.customer;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CustomerRepository {

    private final Map<String, Customer> customerMap = new HashMap<>();

    public Set<Customer> getCustomers() {
        return new HashSet<>(customerMap.values());
    }

    public Customer createCustomer(Customer customer) {
        Customer customerToCreate = null;
        if (!customerMap.containsKey(customer.getEmail())) {
            customerToCreate = getCustomer(customerMap.size() + 1, customer.getName(), customer.getEmail());
            customerToCreate.setAddressId(customer.getAddressId());
            customerMap.put(customerToCreate.getEmail(), customerToCreate);
        }

        return customerToCreate;
    }

    public Customer updateCustomer(Customer customer) {
        Customer customerToUpdate = null;

        if (customerMap.containsKey(customer.getEmail())) {
            customerToUpdate = customerMap.get(customer.getEmail());
            customerToUpdate.setName(customer.getName());
            customerToUpdate.setEmail(customer.getEmail());
            customerToUpdate.setAddressId(customer.getAddressId());
        }

        return customerToUpdate;
    }

    public Customer deleteCustomer(Customer customer) {
        Customer customerToDelete = null;
        if (customerMap.containsKey(customer.getEmail())) {
            customerToDelete = customerMap.get(customer.getEmail());
            customerMap.remove(customer.getEmail());
        }

        return customerToDelete;
    }

    public List<Customer> createCustomers(List<Customer> customers) {
        List<Customer> newCustomers = new ArrayList<>();
        customers.forEach(customer -> newCustomers.add(createCustomer(customer)));
        return newCustomers;
    }

    public List<Customer> updateCustomers(List<Customer> customers) {
        List<Customer> updatedCustomers = new ArrayList<>();
        customers.forEach(customer -> updatedCustomers.add(updateCustomer(customer)));
        return updatedCustomers;
    }

    public List<Customer> deleteCustomers(List<Customer> customers) {
        List<Customer> deletedCustomers = new ArrayList<>();
        customers.forEach(customer -> deletedCustomers.add(deleteCustomer(customer)));
        return deletedCustomers;
    }

    private static Customer getCustomer(int id, String name, String email) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        customer.setEmail(email);
        return customer;
    }
}
