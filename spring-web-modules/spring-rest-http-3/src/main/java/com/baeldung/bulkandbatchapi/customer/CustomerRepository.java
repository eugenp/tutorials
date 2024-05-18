package com.baeldung.bulkandbatchapi.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CustomerRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerRepository.class);
    private final Map<Integer, Customer> customerMap = new HashMap<>();

    public Set<Customer> getCustomers() {
        return new HashSet<>(customerMap.values());
    }

    public Customer createCustomer(Customer customer) {
        Customer customerToCreate = null;

        if (customer.getId() == 0 && !customerMap.containsKey(customer.getId())) {
            customerToCreate = getCustomer(customerMap.size() + 1, customer.getName(), customer.getEmail());
            customerToCreate.setAddressId(customer.getAddressId());
            customerMap.put(customerToCreate.getId(), customerToCreate);

            LOGGER.info("Created Customer : {}", customerToCreate);
        }

        return customerToCreate;
    }

    public Customer updateCustomer(Customer customer) {
        Customer customerToUpdate = null;

        if (customer.getId() != 0 && customerMap.containsKey(customer.getId())) {
            customerToUpdate = customerMap.get(customer.getId());

            if (customerToUpdate.getId() == customer.getId()) {
                customerToUpdate.setName(customer.getName());
                customerToUpdate.setEmail(customer.getEmail());
                customerToUpdate.setAddressId(customer.getAddressId());

                LOGGER.info("Updated Customer : {}", customerToUpdate);
            }
        }

        return customerToUpdate;
    }

    public Customer deleteCustomer(Customer customer) {
        Customer customerToDelete = null;
        if (customerMap.containsKey(customer.getId())) {
            customerToDelete = customerMap.get(customer.getId());
            if (customerToDelete.getId() == customer.getId()) {
                customerMap.remove(customer.getId());

                LOGGER.info("Deleted Customer : {}", customerToDelete);
            }
        }

        return customerToDelete;
    }

    public List<Customer> createCustomers(List<Customer> customers) {
        List<Customer> newCustomers = new ArrayList<>();
        customers.forEach(customer ->
        {
            LOGGER.info("Creating Customer : {}", customer);
            Customer customer1 = createCustomer(customer);
            if (customer1 != null) {
                newCustomers.add(customer1);
            }
        });
        return newCustomers;
    }

    public List<Customer> updateCustomers(List<Customer> customers) {
        List<Customer> updatedCustomers = new ArrayList<>();
        customers.forEach(customer ->
        {
            LOGGER.info("Updating Customer : {}", customer);
            Customer customer1 = updateCustomer(customer);
            if (customer1 != null) {
                updatedCustomers.add(customer1);
            }
        });
        return updatedCustomers;
    }

    public List<Customer> deleteCustomers(List<Customer> customers) {
        List<Customer> deletedCustomers = new ArrayList<>();
        customers.forEach(customer ->
        {
            if (customer.getId() != 0) {
                Customer customer1 = deleteCustomer(customer);
                if (customer1 != null) {
                    deletedCustomers.add(customer1);
                }
            }
        });
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
