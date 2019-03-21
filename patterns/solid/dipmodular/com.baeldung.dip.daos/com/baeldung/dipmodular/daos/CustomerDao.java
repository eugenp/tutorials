package com.baeldung.dipmodular.daos;

import com.baeldung.dipmodular.entities.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerDao {

    Optional<Customer> findById(int id);

    List<Customer> findAll();

}
