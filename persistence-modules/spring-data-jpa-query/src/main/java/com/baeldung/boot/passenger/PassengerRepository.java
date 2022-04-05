package com.baeldung.boot.passenger;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

interface PassengerRepository extends JpaRepository<Passenger, Long>, CustomPassengerRepository {

    Passenger findFirstByOrderBySeatNumberAsc();

    Passenger findTopByOrderBySeatNumberAsc();

    List<Passenger> findByOrderBySeatNumberAsc();

    List<Passenger> findByFirstNameIgnoreCase(String firstName);

    List<Passenger> findByLastNameOrderBySeatNumberAsc(String lastName);

    List<Passenger> findByLastName(String lastName, Sort sort);

}
