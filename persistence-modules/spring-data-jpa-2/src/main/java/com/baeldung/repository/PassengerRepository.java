package com.baeldung.repository;

import com.baeldung.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
interface PassengerRepository extends JpaRepository<Passenger, Long>, CustomPassengerRepository {

    List<Passenger> findByFirstNameIgnoreCase(String firstName);

}
