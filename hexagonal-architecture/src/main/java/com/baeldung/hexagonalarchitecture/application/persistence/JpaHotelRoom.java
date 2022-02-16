package com.baeldung.hexagonalarchitecture.application.persistence;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ROOMS")
public class JpaHotelRoom {

    public enum Status {
        AVAILABLE, MAINTENANCE, CLEANING, UNAVAILABLE
    }

    @Id
    private String roomNumber;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ElementCollection
    private Set<JpaBooking> bookings = new HashSet<>();

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<JpaBooking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<JpaBooking> bookings) {
        this.bookings = bookings;
    }
}
