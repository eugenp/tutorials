package com.baeldung.spring.modulith.cqrs.movie.seating.internal;

import static java.time.temporal.ChronoUnit.HOURS;

import java.time.Instant;
import java.util.List;
import java.util.stream.LongStream;

import org.jmolecules.event.annotation.DomainEventHandler;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import com.baeldung.spring.modulith.cqrs.ticket.booking.api.BookingCancelled;
import com.baeldung.spring.modulith.cqrs.ticket.booking.api.BookingCreated;

@Component
class TicketBookingEventHandler {

    private final ScreenRoomRepository screenRooms;

    TicketBookingEventHandler(ScreenRoomRepository screenRooms) {
        this.screenRooms = screenRooms;
    }

    @DomainEventHandler
    @ApplicationModuleListener
    void handleTicketBooked(BookingCreated booking) {
        ScreenRoom room = screenRooms.findByMovieId(booking.movieId())
            .orElseThrow();

        room.occupySeat(booking.seatNumber());
        screenRooms.save(room);
    }

    @DomainEventHandler
    @ApplicationModuleListener
    void handleTicketCancelled(BookingCancelled cancellation) {
        ScreenRoom room = screenRooms.findByMovieId(cancellation.movieId())
            .orElseThrow();

        room.freeSeat(cancellation.seatNumber());
        screenRooms.save(room);
    }

    @EventListener(ApplicationReadyEvent.class)
    void insertDummyMovies() {
        List<ScreenRoom> dummyMovies = LongStream.range(1, 30)
            .mapToObj(nr ->
                new ScreenRoom(nr, "Dummy movie #" + nr, Instant.now().plus(nr, HOURS)))
            .toList();
        screenRooms.saveAll(dummyMovies);
    }

}
