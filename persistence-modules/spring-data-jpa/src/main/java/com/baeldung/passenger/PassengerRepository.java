package com.baeldung.passenger;

import org.springframework.data.jpa.repository.JpaRepository;

interface PassengerRepository extends JpaRepository<Passenger, Long>, CustomPassengerRepository {

    Passenger findFirstByOrderBySeatNumberAsc();
}
