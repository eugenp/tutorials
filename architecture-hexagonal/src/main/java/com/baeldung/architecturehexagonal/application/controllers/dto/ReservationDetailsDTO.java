package com.baeldung.architecturehexagonal.application.controllers.dto;

import com.baeldung.architecturehexagonal.domain.model.Reservation;

import lombok.Value;

@Value
public class ReservationDetailsDTO {
    Long id;
    Integer tableNumber;

    public static ReservationDetailsDTO from(Reservation reservation) {
        return new ReservationDetailsDTO(reservation.getId(), reservation.getTable()
            .getNumber());
    }
}
