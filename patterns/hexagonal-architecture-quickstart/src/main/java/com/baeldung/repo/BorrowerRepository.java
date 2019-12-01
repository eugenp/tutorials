package com.baeldung.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.pojo.Borrower;

@Repository
public interface BorrowerRepository extends CrudRepository<Borrower,String> {

}
