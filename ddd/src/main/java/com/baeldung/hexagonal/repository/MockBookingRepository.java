package com.baeldung.hexagonal.repository;

import com.baeldung.hexagonal.domain.Booking;
import org.springframework.stereotype.Repository;

@Repository
public class MockBookingRepository implements BookingRepository {
    public boolean save(Booking booking) {
        return true;
    }

    @Override
    public boolean updateStatus(String bookingId, String status) {
        return true;
    }
}
