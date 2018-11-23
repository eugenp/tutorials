package com.baeldung.ports;

import com.baeldung.domain.Booking;
import com.baeldung.domain.UnableToBookException;

public interface BookingService {
    Booking place(BookingRequest bookingRequest) throws UnableToBookException;
}
