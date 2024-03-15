package com.baeldung.fetchandrefresh;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.customerName = :customerName AND o.orderDate BETWEEN :startDate AND :endDate")
    List<Order> findOrdersByCustomerAndDateRange(@Param("customerName") String customerName, @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate);
}
