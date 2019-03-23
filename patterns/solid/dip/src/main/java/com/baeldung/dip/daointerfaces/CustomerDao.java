package com.baeldung.dip.daointerfaces;

import com.baeldung.dip.entities.Customer;
import java.util.Map;
import java.util.Optional;

public interface CustomerDao {

    Optional<Customer> findById(int id);

    Map<Integer, Customer> findAll();

}
