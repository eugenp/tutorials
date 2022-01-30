package com.baeldung.architecturehexagonal.infrastructure.repositories.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.baeldung.architecturehexagonal.domain.model.Reservation;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReservationDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private Integer persons;
    @OneToOne
    private TableDAO table;

    public static ReservationDAO from(Reservation reservation) {
        return new ReservationDAO(reservation.getId(), reservation.getCustomerName(), reservation.getPersons(), TableDAO.fromTable(reservation.getTable()));
    }

    public Reservation toReservation() {
        return Reservation.of(this.id, this.customerName, this.persons, this.table.toTable());
    }
}
