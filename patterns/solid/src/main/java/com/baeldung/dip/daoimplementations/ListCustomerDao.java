package com.baeldung.dip.daoimplementations;

import com.baeldung.dip.entities.Customer;
import com.baeldung.dip.daointerfaces.CustomerDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListCustomerDao implements CustomerDao {

    private List<Customer> customers = new ArrayList<>();

    public ListCustomerDao(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public Optional<Customer> findById(int id) {
        return Optional.ofNullable(getByIndex(id));
    }

    @Override
    public List<Customer> findAll() {
        return customers;
    }

    private Customer getByIndex(int id) {
        try {
            return customers.get(id);
        } catch (IndexOutOfBoundsException e) {
            return customers.get(0);
        }
    }
}
