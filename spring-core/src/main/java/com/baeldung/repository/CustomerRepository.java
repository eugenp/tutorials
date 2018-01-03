/**
 * 
 */
package com.baeldung.repository;

import java.util.List;

import com.baeldung.model.Customer;

public interface CustomerRepository {

    public List<Customer> findAll();
}
