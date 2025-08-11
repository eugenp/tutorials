package com.baeldung.spring.modulith.cqrs.ticket;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
class BookedTicket {

    @Id
    @GeneratedValue
    private Long id;
    private Long movieId;
    private String seatNumber;
    private Instant createdAt = Instant.now();
    private Status status = Status.BOOKED;

    BookedTicket cancelledBooking() {
        BookedTicket cancelled = new BookedTicket(movieId, seatNumber);
        cancelled.status = Status.BOOKING_CANCELLED;
        return cancelled;
    }

    enum Status {
        BOOKED,
        BOOKING_CANCELLED
    }

    boolean isBooked() {
        return status == Status.BOOKED;
    }

    boolean isCancelled() {
        return status == Status.BOOKING_CANCELLED;
    }

    BookedTicket(Long movieId, String seatNumber) {
        this.movieId = movieId;
        this.seatNumber = seatNumber;
    }

    Long id() {
        return id;
    }

    Long movieId() {
        return movieId;
    }

    String seatNumber() {
        return seatNumber;
    }

    Instant createdAt() {
        return createdAt;
    }

    Status status() {
        return status;
    }

    protected BookedTicket() {
        // Default constructor for JPA
    }
}
