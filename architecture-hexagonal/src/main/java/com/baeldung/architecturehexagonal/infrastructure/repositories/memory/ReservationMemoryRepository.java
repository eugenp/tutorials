package com.baeldung.architecturehexagonal.infrastructure.repositories.memory;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.baeldung.architecturehexagonal.domain.model.Reservation;
import com.baeldung.architecturehexagonal.domain.model.Table;
import com.baeldung.architecturehexagonal.domain.ports.repositories.IReservationRepository;

public class ReservationMemoryRepository implements IReservationRepository {
    public ReservationMemoryRepository() {
        this.reservations = new HashSet<>();
    }

    private final Set<Reservation> reservations;

    @Override
    public Optional<Reservation> getCustomerReservation(String customerName) {
        return reservations.stream().filter(r->r.getCustomerName().equals(customerName)).findFirst();
    }

    @Override
    public Set<Reservation> getTableReservations(Set<Table> tables) {
        return reservations.stream().filter(r->tables.contains(r.getTable())).collect(Collectors.toSet());
    }

    @Override
    public Reservation save(Reservation reservation) {
        reservations.add(reservation);
        return reservation;
    }
}
