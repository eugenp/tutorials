package com.baeldung.partialupdate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.partialupdate.model.CustomerStructured;

@Repository
public interface CustomerStructuredRepository extends CrudRepository<CustomerStructured, Long> {
    CustomerStructured findById(long id);
}