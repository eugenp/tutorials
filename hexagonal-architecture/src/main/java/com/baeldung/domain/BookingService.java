package com.baeldung.domain;

import com.baeldung.ports.BookingRepository;
import com.baeldung.ports.BookingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingService implements com.baeldung.ports.BookingService {

    @Autowired
    private BookingRepository bookings;

    @Override
    public Booking place(BookingRequest bookingRequest) throws UnableToBookException {
        Booking booking = new Booking(bookingRequest);
        bookings.save(booking);
        return booking;
    }
}
