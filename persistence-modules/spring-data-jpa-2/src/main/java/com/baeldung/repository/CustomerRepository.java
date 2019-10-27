package com.baeldung.repository;

import com.baeldung.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByName(String name);

    List<Customer> findByNameAndEmail(String name, String email);

    @Query("SELECT c FROM Customer c WHERE (:name is null or c.name = :name) and (:email is null or c.email = :email)")
    List<Customer> findCustomerByNameAndEmail(@Param("name") String name, @Param("email") String email);

}
