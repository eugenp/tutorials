package com.baeldung.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByName(String name);

    List<Customer> findByNameAndEmail(String name, String email);

    @Query("SELECT s FROM Customer s WHERE (:name is null or s.name = :name) and (:email is null or s.email = :email)")
    List<Customer> findCustomerByNameAndEmail(@Param("name") String name, @Param("email") String email);

}
