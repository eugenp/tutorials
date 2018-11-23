package com.baeldung.adapters;

import com.baeldung.domain.Booking;
import com.baeldung.domain.UnableToBookException;
import com.baeldung.ports.BookingRequest;
import com.baeldung.ports.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingRestController {

    @Autowired
    BookingService bookingService;

    @PostMapping(value = "/bookings")
    public Booking createBooking(@RequestBody BookingRequest request) throws UnableToBookException {
        return bookingService.place(request);
    }
}
