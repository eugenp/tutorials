package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.domain.Booking;
import com.baeldung.hexagonal.port.BookingPersistencePort;
import com.baeldung.hexagonal.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingPersistenceAdapter implements BookingPersistencePort {

    @Autowired
    private BookingRepository bookingRepository;

    public boolean persist(Booking booking) {
        return bookingRepository.save(booking);
    }
}
