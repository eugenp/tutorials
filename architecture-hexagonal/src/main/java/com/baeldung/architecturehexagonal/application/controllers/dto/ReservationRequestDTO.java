package com.baeldung.architecturehexagonal.application.controllers.dto;

import com.baeldung.architecturehexagonal.domain.ports.requests.ReservationRequest;

import lombok.Value;

@Value
public class ReservationRequestDTO {
    String restaurantName;
    String customerName;
    Integer numberOfPersons;

    public ReservationRequest toReservationRequest() {
        return new ReservationRequest(restaurantName, customerName, numberOfPersons);
    }
}
