package com.baeldung.partialupdate.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.baeldung.partialupdate.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findById(long id);

    @Modifying
    @Query("update Customer u set u.phone = :phone where u.id = :id")
    void updatePhone(@Param(value = "id") long id, @Param(value = "phone") String phone);
}