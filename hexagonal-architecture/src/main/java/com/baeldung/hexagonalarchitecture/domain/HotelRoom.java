package com.baeldung.hexagonalarchitecture.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.baeldung.hexagonalarchitecture.domain.Booking.PeriodDetails;

public class HotelRoom {

    public enum Status {
        AVAILABLE, ROUTINE_MAINTENANCE, HOSPITALITY_CLEANING, TEMPORARY_UNAVAILABLE
    }

    private final String roomNumber;
    private Status status;
    private Map<UUID, Booking> bookings;

    public HotelRoom(String roomNumber) {
        this.roomNumber = roomNumber;
        this.status = Status.AVAILABLE;
        this.bookings = new HashMap();
    }

    public HotelRoom(String roomNumber, Status status, Map<UUID, Booking> bookings) {
        this.roomNumber = roomNumber;
        this.status = status;
        this.bookings = bookings;
    }

    public void book(Booking booking) {
        if (status != Status.AVAILABLE) {
            throw new IllegalStateException(String.format("the room %s is not AVAILABLE for booking.", roomNumber));
        }
        checkAvailability(booking.getPeriodDetails());
        bookings.put(booking.getUuid(), booking);
    }

    public void startRoutineMaintenance() {
        validateRoomStatus();
        this.status = Status.ROUTINE_MAINTENANCE;
    }

    public void startHospitalityCleaning() {
        validateRoomStatus();
        this.status = Status.HOSPITALITY_CLEANING;
    }

    private void validateRoomStatus() {
        // business logic
    }

    private void checkAvailability(PeriodDetails period) {
        // business logic
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public Status getStatus() {
        return status;
    }

    public Map<UUID, Booking> getBookings() {
        return bookings;
    }
}