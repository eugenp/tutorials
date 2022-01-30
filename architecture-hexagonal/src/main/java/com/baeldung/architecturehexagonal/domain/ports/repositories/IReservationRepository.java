package com.baeldung.architecturehexagonal.domain.ports.repositories;

import java.util.Optional;
import java.util.Set;

import com.baeldung.architecturehexagonal.domain.model.Reservation;
import com.baeldung.architecturehexagonal.domain.model.Table;

public interface IReservationRepository {
    Optional<Reservation> getCustomerReservation(String customerName);
    Set<Reservation> getTableReservations(Set<Table> tables);
    Reservation save(Reservation reservation);
}
