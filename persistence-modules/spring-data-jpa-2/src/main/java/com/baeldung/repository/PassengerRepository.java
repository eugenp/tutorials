package com.baeldung.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.baeldung.entity.Passenger;

@Repository
interface PassengerRepository extends JpaRepository<Passenger, Long>, CustomPassengerRepository {

    List<Passenger> findByFirstNameIgnoreCase(String firstName);

}
