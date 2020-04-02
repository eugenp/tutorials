package com.baeldung.springboot.hexagonal.domain;

import java.util.List;

public interface ReservationService {
    public List<Flight> identifyFLights(String date, String origin, String destination);

    public Reservation processBooking(Reservation reservation, Long customerId);

    public List<Reservation> bookings(Long customerId);

    public boolean cancel(Reservation file);
}
