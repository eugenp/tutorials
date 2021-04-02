package com.baeldung.hexagonal.domain;

import java.util.Set;

public class Booking {
    private String bookingId;
    private String movieShowId;
    private String theatreId;
    private String customerId;
    private Set<String> seats;
    private Double amount;
    private Status status;

    public enum Status {
        INITIAL, SUCCESS, FAILURE
    }

    public Booking(
            String bookingId, String movieShowId, String theatreId, String customerId, Set<String> seats, Double amount, Status status) {
        this.bookingId = bookingId;
        this.movieShowId = movieShowId;
        this.theatreId = theatreId;
        this.customerId = customerId;
        this.seats = seats;
        this.amount = amount;
        this.status = Status.INITIAL;
    }

    public String getMovieShowId() {
        return movieShowId;
    }

    public String getTheatreId() {
        return theatreId;
    }

    public Set<String> getSeats() {
        return seats;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public Double getAmount() {
        return amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setMovieShowId(String movieShowId) {
        this.movieShowId = movieShowId;
    }

    public void setTheatreId(String theatreId) {
        this.theatreId = theatreId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setSeats(Set<String> seats) {
        this.seats = seats;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
