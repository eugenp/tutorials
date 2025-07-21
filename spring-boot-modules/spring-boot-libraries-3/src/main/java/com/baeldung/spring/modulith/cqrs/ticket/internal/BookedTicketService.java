package com.baeldung.spring.modulith.cqrs.ticket.internal;

import org.jmolecules.architecture.cqrs.CommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.baeldung.spring.modulith.cqrs.ticket.BookTicket;
import com.baeldung.spring.modulith.cqrs.ticket.BookingCancelled;
import com.baeldung.spring.modulith.cqrs.ticket.BookingCreated;
import com.baeldung.spring.modulith.cqrs.ticket.CancelTicket;

import jakarta.transaction.Transactional;

@Service
@Transactional
class BookedTicketService implements BookedTicketsCommandHandler {

    private static final Logger log = LoggerFactory.getLogger(BookedTicketService.class);
    private final BookedTicketRepository bookedTickets;
    private final ApplicationEventPublisher eventPublisher;

    BookedTicketService(BookedTicketRepository tickets, ApplicationEventPublisher eventPublisher) {
        this.bookedTickets = tickets;
        this.eventPublisher = eventPublisher;
    }

    @CommandHandler
    public Long bookTicket(BookTicket booking) {
        log.info("Received booking command for movie ID: {}, seat: {}. checking availability...", booking.movieId(), booking.seat());

        validateSeatNumber(booking.seat());

        bookedTickets.findLatestByMovieIdAndSeatNumber(booking.movieId(), booking.seat())
            .filter(BookedTicket::isBooked)
            .ifPresent(it -> {
                log.error("Seat {} is already booked for movie ID: {}. Booking cannot proceed.", booking.seat(), booking.movieId());
                throw new IllegalStateException("Seat %s is already booked for movie ID: %s".formatted(booking.seat(), booking.movieId()));
            });

        log.info("Seat: {} is available for movie ID: {}. Proceeding with booking.", booking.seat(), booking.movieId());

        BookedTicket bookedTicket = new BookedTicket(booking.movieId(), booking.seat());
        bookedTicket = bookedTickets.save(bookedTicket);

        eventPublisher.publishEvent(new BookingCreated(bookedTicket.getMovieId(), bookedTicket.getSeatNumber()));

        return bookedTicket.getId();
    }

    public Long cancelTicket(CancelTicket cancellation) {
        log.info("Received cancellation command for bookingId: {}. Validating the Booking", cancellation.bookingId());

        BookedTicket booking = bookedTickets.findById(cancellation.bookingId())
            .orElseThrow(() -> new IllegalArgumentException("Booking not found for ID: " + cancellation.bookingId()));

        if (booking.isCancelled()) {
            log.warn("Booking with ID: {} is already cancelled. No action taken.", cancellation.bookingId());
            throw new IllegalStateException("Booking with ID: " + cancellation.bookingId() + " is already cancelled.");
        }

        log.info("Proceeding with cancellation for {}", cancellation.bookingId());
        BookedTicket cancelledTicket = booking.cancelledBooking();
        bookedTickets.save(cancelledTicket);

        eventPublisher.publishEvent(new BookingCancelled(cancelledTicket.getMovieId(), cancelledTicket.getSeatNumber()));

        return cancelledTicket.getId();
    }

    private static void validateSeatNumber(String seat) {
        if (seat == null || seat.isBlank()) {
            throw new IllegalArgumentException("Seat number cannot be null or empty");
        }

        char col = seat.charAt(0);
        if (col < 'A' || col > 'J') {
            throw new IllegalArgumentException("Invalid seat number: " + seat);
        }

        int rowPart = Integer.parseInt(seat.substring(1));
        if (rowPart < 1 || rowPart > 20) {
            throw new IllegalArgumentException("Invalid seat number: " + seat);
        }
    }

}