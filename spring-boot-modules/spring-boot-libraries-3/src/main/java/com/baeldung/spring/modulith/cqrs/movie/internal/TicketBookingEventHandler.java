package com.baeldung.spring.modulith.cqrs.movie.internal;

import static java.time.temporal.ChronoUnit.HOURS;

import java.time.Instant;
import java.util.List;
import java.util.stream.LongStream;

import org.jmolecules.event.annotation.DomainEventHandler;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import com.baeldung.spring.modulith.cqrs.ticket.BookingCancelled;
import com.baeldung.spring.modulith.cqrs.ticket.BookingCreated;

@Component
class TicketBookingEventHandler {

    private final MovieRepository screenRooms;

    TicketBookingEventHandler(MovieRepository screenRooms) {
        this.screenRooms = screenRooms;
    }

    @DomainEventHandler
    @ApplicationModuleListener
    void handleTicketBooked(BookingCreated booking) {
        Movie room = screenRooms.findById(booking.movieId())
            .orElseThrow();

        room.occupySeat(booking.seatNumber());
        screenRooms.save(room);
    }

    @DomainEventHandler
    @ApplicationModuleListener
    void handleTicketCancelled(BookingCancelled cancellation) {
        Movie room = screenRooms.findById(cancellation.movieId())
            .orElseThrow();

        room.freeSeat(cancellation.seatNumber());
        screenRooms.save(room);
    }

    @EventListener(ApplicationReadyEvent.class)
    void insertDummyMovies() {
        List<Movie> dummyMovies = LongStream.range(1, 30)
            .mapToObj(nr -> new Movie("Dummy movie #" + nr, "Screen #" + nr % 5, Instant.now()
                .plus(nr, HOURS)))
            .toList();
        screenRooms.saveAll(dummyMovies);
    }

}
