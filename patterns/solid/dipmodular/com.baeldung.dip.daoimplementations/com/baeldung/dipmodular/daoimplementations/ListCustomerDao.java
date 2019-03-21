package com.baeldung.dipmodularmodular.daoimplementations;

import com.baeldung.dipmodular.daos.CustomerDao;
import com.baeldung.dipmodular.entities.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListCustomerDao implements CustomerDao {

    private List<Customer> customers = new ArrayList<>();

    public ListCustomerDao() {
    }

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
