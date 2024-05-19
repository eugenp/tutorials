package com.baeldung.bulkandbatchapi.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CustomerRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerRepository.class);
    private final Map<String, Customer> customerByEmailMap = new HashMap<>();

    public List<Customer> getCustomers() {
        return new ArrayList<>(customerByEmailMap.values());
    }

    public List<Customer> createCustomers(List<Customer> customers) {
        List<Customer> newCustomers = new ArrayList<>();

        customers.forEach(customer ->
        {
            LOGGER.info("Creating Customer : {}", customer);
            Customer customerCreated = createCustomer(customer);
            if (customerCreated != null) {
                newCustomers.add(customerCreated);
            }
        });
        return newCustomers;
    }

    public List<Customer> updateCustomers(List<Customer> customers) {
        List<Customer> updatedCustomers = new ArrayList<>();

        customers.forEach(customer ->
        {
            LOGGER.info("Updating Customer : {}", customer);
            Customer customerUpdated = updateCustomer(customer);
            if (customerUpdated != null) {
                updatedCustomers.add(customerUpdated);
            }
        });

        return updatedCustomers;
    }

    public List<Customer> deleteCustomers(List<Customer> customers) {
        List<Customer> deletedCustomers = new ArrayList<>();

        customers.forEach(customer ->
        {
            LOGGER.info("Deleting Customer : {}", customer);
            Customer customerDeleted = deleteCustomer(customer);
            if (customerDeleted != null) {
                deletedCustomers.add(customerDeleted);
            }
        });

        return deletedCustomers;
    }

    public Customer createCustomer(Customer customer) {
        Customer customerToCreate = null;

        if (!customerByEmailMap.containsKey(customer.getEmail()) && customer.getId() == 0) {
            customerToCreate = getCustomer(customerByEmailMap.size() + 1, customer.getName(), customer.getEmail());
            customerToCreate.setAddress(customer.getAddress());
            customerByEmailMap.put(customerToCreate.getEmail(), customerToCreate);

            LOGGER.info("Created Customer : {}", customerToCreate);
        }

        return customerToCreate;
    }

    public Customer updateCustomer(Customer customer) {
        Customer customerToUpdate = null;
        customerToUpdate = customerByEmailMap.get(customer.getEmail());

        if (customerToUpdate != null && customerToUpdate.getId() == customer.getId()) {
            customerToUpdate.setName(customer.getName());
            customerToUpdate.setAddress(customer.getAddress());

            LOGGER.info("Updated Customer : {}", customerToUpdate);
        }

        return customerToUpdate;
    }

    private Customer deleteCustomer(Customer customer) {
        Customer customerToDelete = null;

        if (customerByEmailMap.containsKey(customer.getEmail())) {
            customerToDelete = customerByEmailMap.get(customer.getEmail());
            if (customerToDelete.getId() == customer.getId()) {
                customerByEmailMap.remove(customer.getEmail());

                LOGGER.info("Deleted Customer : {}", customerToDelete);
            }
        }

        return customerToDelete;
    }

    private static Customer getCustomer(int id, String name, String email) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        customer.setEmail(email);

        return customer;
    }
}
