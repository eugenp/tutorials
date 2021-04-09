package com.baeldung.hexagonal.port;

import com.baeldung.hexagonal.domain.Booking;

public interface BookingPersistencePort {
    boolean persist(Booking booking);
    public boolean updateStatus(String bookingId, String status);
}
