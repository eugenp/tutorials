package com.baeldung.architecturehexagonal.application.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.architecturehexagonal.domain.ports.errors.ReservationSystemError;
import com.baeldung.architecturehexagonal.domain.ports.errors.RestaurantNotFound;
import com.baeldung.architecturehexagonal.domain.usecases.GetCurrentReservedCapacity;
import com.baeldung.architecturehexagonal.domain.usecases.Reserve;
import com.baeldung.architecturehexagonal.application.controllers.dto.ReservationDetailsDTO;
import com.baeldung.architecturehexagonal.application.controllers.dto.ReservationRequestDTO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/reservations")
public class ReservationController {

    GetCurrentReservedCapacity getCurrentReservedCapacity;
    Reserve reserve;

    @GetMapping("/{restaurantName}")
    public ResponseEntity<Integer> getReservations(@PathVariable String restaurantName) {
        try {
            return ResponseEntity.ok(getCurrentReservedCapacity.perform(restaurantName));
        } catch (RestaurantNotFound e) {
            log.warn(e.getClass() + " - " +e.getMessage());
            return ResponseEntity.badRequest()
                .build();
        }
    }

    @PostMapping
    public ResponseEntity<ReservationDetailsDTO> reserve(@RequestBody ReservationRequestDTO reservationRequestDTO) {
        try {
            return ResponseEntity.ok(ReservationDetailsDTO.from(reserve.perform(reservationRequestDTO.toReservationRequest())));
        } catch (ReservationSystemError e) {
            log.warn(e.getClass() + " - " +e.getMessage());
            return ResponseEntity.badRequest()
                .build();
        }
    }
}
