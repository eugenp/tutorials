package com.baeldung.hexagon.adapter;

import java.time.LocalDateTime;
import java.util.List;
import com.baeldung.hexagon.app.MovieTicketBookingSystem;
import com.baeldung.hexagon.model.Ticket;
import com.baeldung.hexagon.port.MovieTicketBookingPort;
import com.baeldung.hexagon.util.ValidationException;

class MovieTicketBookingAdapter implements MovieTicketBookingPort {
    private MovieTicketBookingSystem bookingSystem;

    MovieTicketBookingAdapter(MovieTicketBookingSystem bookingSystem) {
        this.bookingSystem = bookingSystem;
    }

    @Override
    public List<Ticket> bookTickets(String movie, LocalDateTime timestamp, int seatCount) {
        if (movie == null) {
            throw new ValidationException("Movie not selected");
        }
        if (LocalDateTime.now().compareTo(timestamp) > 0) {
            throw new ValidationException("Selected date & time is invalid");
        }
        if (seatCount < 1) {
            throw new ValidationException("Seat count must be greater than 0");
        }
        return bookingSystem.bookTickets(movie, timestamp, seatCount);
    }
}
