package com.baeldung.springboot.hexagonal.application;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.springboot.hexagonal.domain.Flight;
import com.baeldung.springboot.hexagonal.domain.Reservation;
import com.baeldung.springboot.hexagonal.domain.ReservationService;
import com.baeldung.springboot.hexagonal.infrastructure.customerjpa.ReservationFile;
import com.baeldung.springboot.hexagonal.infrastructure.customerjpa.ReservationFileRepository;

@RestController
public class BookingController {
    private ReservationService reservationService;
    private ReservationFileRepository repo;

    public BookingController(ReservationService reservationService, ReservationFileRepository repo) {
        this.reservationService = reservationService;
    }

    @GetMapping("/findFlights")
    public List<Flight> getTravel(@RequestParam("date") String date, @RequestParam("origin") String origin,
            @RequestParam("destination") String destination) {
        return reservationService.identifyFLights(date, origin, destination);
    }

    @PutMapping("/customer/{id}/booking")
    public Reservation makeBooking(@PathVariable("id") Long customerId, @RequestBody Reservation reservation) {
        return reservationService.processBooking(reservation, customerId);
    }

    @GetMapping("/customer/{customerId}/booking")
    public List<Reservation> bookings(@PathVariable("customerId") Long id) {
        return reservationService.bookings(id);
    }

    @DeleteMapping("/customer/{customerId}/booking/{id}")
    public boolean cancelBooking(@PathVariable("customerId") Long customerId, @PathVariable("id") UUID reservationId) {
        Reservation reservation = new Reservation();
        reservation.setCustomerId(customerId);
        reservation.setId(reservationId);
        return reservationService.cancel(reservation);
    }

    @GetMapping("/booking")
    public List<ReservationFile> booking() {
        return repo.findAll();
    }
}
