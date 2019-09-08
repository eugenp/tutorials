package com.baeldung.boot.passenger;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

interface PassengerRepository extends JpaRepository<Passenger, Long>, CustomPassengerRepository {

    Passenger findFirstByOrderBySeatNumberAsc();

    Passenger findTopByOrderBySeatNumberAsc();

    List<Passenger> findByOrderBySeatNumberAsc();

    List<Passenger> findByFirstNameIgnoreCase(String firstName);

}
