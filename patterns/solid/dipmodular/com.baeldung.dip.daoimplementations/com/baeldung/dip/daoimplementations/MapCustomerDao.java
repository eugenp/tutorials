package com.baeldung.dip.daoimplementations;

import com.baeldung.dip.daos.CustomerDao;
import com.baeldung.dip.entities.Customer;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MapCustomerDao implements CustomerDao {

    private Map<Integer, Customer> customers = new HashMap<>();

    public MapCustomerDao() {

    }

    public MapCustomerDao(Map<Integer, Customer> customers) {
        this.customers = customers;
    }

    @Override
    public Optional<Customer> findById(int id) {
        return Optional.ofNullable(customers.get(id));
    }

    @Override
    public Map<Integer, Customer> findAll() {
        return customers;
    }
}
