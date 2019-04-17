package com.baeldung.passenger;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface PassengerRepository extends JpaRepository<Passenger, Long>, CustomPassengerRepository {

    Optional<Passenger> findFirstByOrderBySeatNumberAsc();

    Optional<Passenger> findTopByOrderBySeatNumberAsc();

    List<Passenger> findByOrderBySeatNumberAsc();
    
    List<Passenger> findByLastNameOrderBySeatNumberAsc(String lastName);
    
    List<Passenger> findByLastName(String lastName, Sort sort);

    List<Passenger> findByFirstNameIgnoreCase(String firstName);

}
