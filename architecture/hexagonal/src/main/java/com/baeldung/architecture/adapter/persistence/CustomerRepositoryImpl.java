package com.baeldung.architecture.adapter.persistence;

import com.baeldung.architecture.core.domain.Customer;
import com.baeldung.architecture.port.CustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
    private Map<String, Customer> customerMap = new HashMap<String, Customer>();

    @Override
    public List<Customer> getAll() {
        return customerMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public void create(Customer customer) {
        customerMap.put(customer.getName(), customer);

    }

    @Override
    public Customer getByName(String name) {
        return customerMap.get(name);
    }
}
