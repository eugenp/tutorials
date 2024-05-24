package com.baeldung.bulkandbatchapi.customer;

import com.baeldung.bulkandbatchapi.BatchType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);
    private final Map<String, Customer> customerRepoMap = new HashMap<>();

    public List<Customer> getCustomers() {
        return new ArrayList<>(customerRepoMap.values());
    }

    public List<Customer> createCustomers(List<Customer> customers) {
        List<Customer> createdCustomers = new ArrayList<>();

        customers.forEach(customer ->
        {
            Optional<Customer> customerCreated = createCustomer(customer);
            customerCreated.ifPresent(createdCustomers::add);
        });

        return createdCustomers;
    }

    public List<Customer> processCustomers(List<Customer> customers, BatchType batchType) {
        List<Customer> processedCustomers = new ArrayList<>();

        customers.forEach(customer ->
        {
            Optional<Customer> customerProcessed = Optional.empty();
            switch (batchType) {
                case CREATE:
                    customerProcessed = createCustomer(customer);
                    break;
                case UPDATE:
                    customerProcessed = updateCustomer(customer);
                    break;
                case DELETE:
                    customerProcessed = deleteCustomer(customer);
                    break;
                default:
                    break;
            }

            customerProcessed.ifPresent(processedCustomers::add);
        });

        return processedCustomers;
    }

    public Optional<Customer> createCustomer(Customer customer) {
        LOGGER.info("Creating Customer : {}", customer);

        if (!customerRepoMap.containsKey(customer.getEmail()) && customer.getId() == 0) {
            Customer customerToCreate = getCustomer(customerRepoMap.size() + 1, customer.getName(), customer.getEmail());
            customerToCreate.setAddress(customer.getAddress());
            customerRepoMap.put(customerToCreate.getEmail(), customerToCreate);
            LOGGER.info("Created Customer : {}", customerToCreate);

            return Optional.of(customerToCreate);
        }

        return Optional.empty();
    }

    public Optional<Customer> updateCustomer(Customer customer) {
        LOGGER.info("Updating Customer : {}", customer);

        Customer customerToUpdate = customerRepoMap.get(customer.getEmail());

        if (customerToUpdate != null && customerToUpdate.getId() == customer.getId()) {
            customerToUpdate.setName(customer.getName());
            customerToUpdate.setAddress(customer.getAddress());

            LOGGER.info("Updated Customer : {}", customerToUpdate);
        }

        return customerToUpdate != null ? Optional.of(customerToUpdate) : Optional.empty();
    }

    private Optional<Customer> deleteCustomer(Customer customer) {
        LOGGER.info("Deleting Customer : {}", customer);
        Customer customerToDelete = customerRepoMap.get(customer.getEmail());

        if (customerToDelete != null && customerToDelete.getId() == customer.getId()) {
            customerRepoMap.remove(customer.getEmail());
            LOGGER.info("Deleted Customer : {}", customerToDelete);
        }

        return customerToDelete != null ? Optional.of(customerToDelete) : Optional.empty();
    }

    private static Customer getCustomer(int id, String name, String email) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        customer.setEmail(email);

        return customer;
    }
}
