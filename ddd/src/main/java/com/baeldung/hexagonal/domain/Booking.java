package com.baeldung.hexagonal.domain;

import com.baeldung.hexagonal.port.BookingServicePort;

import java.util.Set;
import java.util.UUID;

public class Booking {

    public static final String STATUS_INITIAL = "INITIAL";
    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String STATUS_FAILURE = "FAILED";

    private String bookingId;
    private String movieShowId;
    private String customerId;
    private Set<String> seats;
    private Double amount;
    private String status;

    public Booking(
            String bookingId, String movieShowId, String customerId, Set<String> seats, Double amount, String status) {
        this.bookingId = bookingId;
        this.movieShowId = movieShowId;
        this.customerId = customerId;
        this.seats = seats;
        this.amount = amount;
        this.status = status;
    }

    public Booking(BookingServicePort.BookingRequest request) {
        this.bookingId = UUID.randomUUID().toString();
        this.movieShowId = request.getMovieShowId();
        this.customerId = request.getCustomerId();
        this.seats = request.getSeats();
        this.amount = request.getAmount();
        this.status = STATUS_INITIAL;
    }

    public String getMovieShowId() {
        return movieShowId;
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

    public String getStatus() {
        return status;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setMovieShowId(String movieShowId) {
        this.movieShowId = movieShowId;
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

    public void setStatus(String status) {
        this.status = status;
    }
}
