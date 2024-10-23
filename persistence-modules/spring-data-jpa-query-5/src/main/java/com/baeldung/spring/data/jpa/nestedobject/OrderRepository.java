package com.baeldung.spring.data.jpa.nestedobject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerEmail(String email);

    List<Order> findByCustomer_Email(String email);

    @Query("SELECT o FROM Order o WHERE o.customer.email = ?1")
    List<Order> findByCustomerEmailAndJPQL(String email);
}
