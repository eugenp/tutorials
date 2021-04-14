package com.baeldung.ddd.hexagonal.travel.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.ddd.hexagonal.travel.inbound.port.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    String createOrder(@RequestBody final ReservationRequest reservationRequest) {
        return reservationService.reserveTicket(reservationRequest.getSource(), reservationRequest.getDestination(), reservationRequest.getServiceType(), reservationRequest.getPassengers(), reservationRequest.getStartTime());
    }

    @PostMapping("/{id}/cancel")
    void cancelReservation(@PathVariable final String ticketId) {
        reservationService.cancelReservation(ticketId);
    }
}
