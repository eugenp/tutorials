package com.baeldung.springboot.hexagonal.domain;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baeldung.springboot.hexagonal.infrastructure.CustomerFileService;
import com.baeldung.springboot.hexagonal.infrastructure.FlightService;

@Service
public class ReservationServiceImpl implements ReservationService {
    private CustomerFileService customerFileService;
    private FlightService flightService;

    public ReservationServiceImpl(CustomerFileService customerFileService, FlightService flightService) {
        super();
        this.customerFileService = customerFileService;
        this.flightService = flightService;
    }

    @Override
    public List<Flight> identifyFLights(String date, String origin, String destination) {
        return flightService.getRoute(date, origin, destination);
    }

    @Override
    public Reservation processBooking(Reservation reservation, Long customer) {
        reservation.setCustomerId(customer);
        return customerFileService.save(reservation);
    }

    @Override
    public List<Reservation> bookings(Long customerId) {
        List<Reservation> reservations = customerFileService.bookings(customerId);
        return reservations;
    }

    @Override
    public boolean cancel(Reservation file) {
        return customerFileService.delete(file.getCustomerId(), file.getId());
    }
}
