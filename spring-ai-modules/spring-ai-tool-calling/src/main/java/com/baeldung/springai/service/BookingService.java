package com.baeldung.springai.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;

import com.baeldung.springai.tools.BookingConfirmation;

@Service
public class BookingService {

    private final List<BookingConfirmation> bookings = new CopyOnWriteArrayList<>();

    public BookingConfirmation book(String origin, String destination, LocalDate date) {
        String reference = UUID.randomUUID()
            .toString()
            .substring(0, 6)
            .toUpperCase(Locale.ROOT);
        String flightNumber = "BA" + Math.abs((origin + destination).hashCode() % 1000);
        BookingConfirmation confirmation = new BookingConfirmation(reference, flightNumber, date);
        bookings.add(confirmation);
        return confirmation;
    }

    public List<BookingConfirmation> getBookings() {
        return List.copyOf(bookings);
    }
}
