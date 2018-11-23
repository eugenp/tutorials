package com.baeldung.adapters;

import com.baeldung.domain.Booking;
import com.baeldung.domain.UnableToBookException;
import com.baeldung.ports.BookingRequest;
import com.baeldung.ports.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BookingFormController {

    @Autowired
    BookingService bookingService;

    @RequestMapping(value = "/bookings", method = RequestMethod.POST)
    public Booking createBooking(@ModelAttribute BookingRequest request) throws UnableToBookException {
        return bookingService.place(request);
    }
}
