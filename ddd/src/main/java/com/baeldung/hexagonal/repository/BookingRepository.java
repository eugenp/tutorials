package com.baeldung.hexagonal.repository;

import com.baeldung.hexagonal.domain.Booking;

public interface BookingRepository {

    boolean save(Booking booking);
    boolean updateStatus(String bookingId, String status);
}
