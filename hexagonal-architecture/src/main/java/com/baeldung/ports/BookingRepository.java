package com.baeldung.ports;

import com.baeldung.domain.Booking;

public interface BookingRepository {
    void save(Booking booking);
}
