package com.baeldung.architecturehexagonal.infrastructure.repositories;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.baeldung.architecturehexagonal.domain.model.Reservation;
import com.baeldung.architecturehexagonal.domain.model.Table;
import com.baeldung.architecturehexagonal.domain.ports.repositories.IReservationRepository;
import com.baeldung.architecturehexagonal.infrastructure.repositories.dao.ReservationDAO;
import com.baeldung.architecturehexagonal.infrastructure.repositories.dao.TableDAO;
import com.baeldung.architecturehexagonal.infrastructure.repositories.jpa.ReservationJpaRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ReservationRepository implements IReservationRepository {
    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public Optional<Reservation> getCustomerReservation(String customerName) {
        return reservationJpaRepository.findByCustomerName(customerName)
            .map(ReservationDAO::toReservation);
    }

    @Override
    public Set<Reservation> getTableReservations(Set<Table> tables) {
        return reservationJpaRepository.findDistinctByTableIsIn(tables.stream()
                .map(TableDAO::fromTable)
                .collect(Collectors.toSet()))
            .stream()
            .map(ReservationDAO::toReservation)
            .collect(Collectors.toSet());
    }

    @Override
    public Reservation save(Reservation reservation) {
        return reservationJpaRepository.save(ReservationDAO.from(reservation))
            .toReservation();
    }
}
