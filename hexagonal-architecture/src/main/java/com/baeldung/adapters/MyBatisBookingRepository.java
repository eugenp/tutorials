package com.baeldung.adapters;

import com.baeldung.domain.Booking;
import com.baeldung.ports.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyBatisBookingRepository implements BookingRepository {

    @Autowired
    BookingMapper bookingMapper;

    @Override
    public void save(Booking booking) {
        bookingMapper.insert(booking);
    }
}
