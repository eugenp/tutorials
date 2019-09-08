package com.baeldung.boot.passenger;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

interface PassengerRepository extends JpaRepository<Passenger, Long> {

    List<Passenger> findByLastNameOrderBySeatNumberAsc(String lastName);

    List<Passenger> findByLastName(String lastName, Sort sort);

}
