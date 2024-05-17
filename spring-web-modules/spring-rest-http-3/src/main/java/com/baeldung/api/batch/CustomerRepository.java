package com.baeldung.api.batch;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CustomerRepository {

    private final Map<String, Customer> customerMap = new HashMap<>();

    public Set<Customer> createCustomers(Set<Customer> customers) {
        Set<Customer> newCUstomers = new HashSet<>();

        customers.forEach(customer -> {
            if (!customerMap.containsKey(customer.getEmail())) {
                Customer newCustomer = getCustomer(customerMap.size() + 1, customer.getName(), customer.getEmail());
                newCUstomers.add(newCustomer);
                customerMap.put(newCustomer.getEmail(), newCustomer);
            }
        });

        return newCUstomers;
    }

    public Set<Customer> updateCustomers(Set<Customer> customers) {
        Set<Customer> updatedCustomers = new HashSet<>();

        customers.forEach(customer -> {
            if (customerMap.containsKey(customer.getEmail())) {

                Customer customerToUpdate = customerMap.get(customer.getEmail());
                customerToUpdate.setName(customer.getName());
                customerToUpdate.setEmail(customer.getEmail());
                updatedCustomers.add(customerToUpdate);

            }
        });

        return updatedCustomers;
    }

    public Set<Customer> deleteCustomers(Set<Customer> customers) {
        Set<Customer> deletedCustomers = new HashSet<>();

        customers.forEach(customer -> {
            if (customerMap.containsKey(customer.getEmail())) {
                customerMap.remove(customer.getEmail());
                deletedCustomers.add(customer);
            }
        });

        return deletedCustomers;
    }

    public Set<Customer> getCustomers() {
        return new HashSet<>(customerMap.values());
    }

    private static Customer getCustomer(int id, String name, String email) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        customer.setEmail(email);
        return customer;
    }
}
