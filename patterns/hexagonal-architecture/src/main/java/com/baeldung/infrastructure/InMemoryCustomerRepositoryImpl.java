package com.baeldung.infrastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.baeldung.appcore.domain.Customer;
import com.baeldung.appcore.repository.CustomerRepository;
import com.baeldung.infrastructure.util.RepositoryUtil;

public class InMemoryCustomerRepositoryImpl implements CustomerRepository {
    private Map<Integer, Customer> dataStore = new HashMap<>();

    @Override
    public Customer registerCustomer(Customer customer) {
        customer.setCustomerId(RepositoryUtil.getPrimaryKey());
        dataStore.putIfAbsent(customer.getCustomerId(), customer);
        return customer;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        dataStore.put(customer.getCustomerId(), customer);
        return customer;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(dataStore.values());
    }

    @Override
    public Optional<Customer> findCustomerById(int customerId) {
        if (dataStore.containsKey(customerId)) {
            return Optional.of(dataStore.get(customerId));
        }
        return Optional.empty();
    }
}