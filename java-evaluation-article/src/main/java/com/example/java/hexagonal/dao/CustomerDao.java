package com.example.java.hexagonal.dao;

import com.example.java.hexagonal.model.Customer;

public interface CustomerDao {

    Customer getById(long id);
}
