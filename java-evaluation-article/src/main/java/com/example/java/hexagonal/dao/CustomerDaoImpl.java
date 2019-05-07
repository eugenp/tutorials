package com.example.java.hexagonal.dao;

import com.example.java.hexagonal.model.Customer;
import org.springframework.stereotype.Repository;

@Repository("CustomerDao")
public class CustomerDaoImpl implements CustomerDao {

    @Override
    public Customer getById(long customerId) {
        return new Customer(customerId, "example");
    }
}
