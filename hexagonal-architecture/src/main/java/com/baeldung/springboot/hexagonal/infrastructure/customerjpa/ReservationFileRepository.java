package com.baeldung.springboot.hexagonal.infrastructure.customerjpa;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationFileRepository extends JpaRepository<ReservationFile, Long> {
    public List<ReservationFile> findByCustomerId(Long customerId);

    public ReservationFile findByCustomerIdAndReservationId(Long customerId, UUID reservationId);
}
