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

    public BookedTicket cancelledBooking() {
        BookedTicket cancelled = new BookedTicket(movieId, seatNumber);
        cancelled.status = Status.BOOKING_CANCELLED;
        return cancelled;
    }

    public enum Status {
        BOOKED,
        BOOKING_CANCELLED
    }

    public boolean isBooked() {
        return status == Status.BOOKED;
    }

    public boolean isCancelled() {
        return status == Status.BOOKING_CANCELLED;
    }

    public BookedTicket(Long movieId, String seatNumber) {
        this.movieId = movieId;
        this.seatNumber = seatNumber;
    }

    public Long getId() {
        return id;
    }

    public Long getMovieId() {
        return movieId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Status getStatus() {
        return status;
    }

    protected BookedTicket() {
        // Default constructor for JPA
    }
}
