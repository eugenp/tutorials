package com.baeldung.hex.demo.adapter.secondary.persistence.dao;

import com.baeldung.hex.demo.adapter.secondary.persistence.entity.CustomerEntity;
import com.baeldung.hex.demo.adapter.secondary.persistence.repository.CustomerRepository;
import com.baeldung.hex.demo.adapter.secondary.persistence.translator.CustomerEntityAdapter;
import com.baeldung.hex.demo.core.model.Customer;
import com.baeldung.hex.demo.port.secondary.CustomerPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerPersistenceServiceImpl implements CustomerPersistenceService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerEntityAdapter customerEntityAdapter;

    public Customer createCustomer(Customer customer) {
        CustomerEntity customerEntity = customerEntityAdapter.toEntity(customer);
        customerRepository.save(customerEntityAdapter.toEntity(customer));
        return customerEntityAdapter.toDomain(customerEntity);
    }

    public Customer retrieveCustomer(String customerId) {
        CustomerEntity customerEntity = customerRepository.getOne(customerId);
        return customerEntityAdapter.toDomain(customerEntity);
    }

    public Customer updateCustomer(Customer customer) {
        CustomerEntity customerEntity = customerEntityAdapter.toEntity(customer);
        customerRepository.save(customerEntityAdapter.toEntity(customer));
        return customerEntityAdapter.toDomain(customerEntity);
    }
}
