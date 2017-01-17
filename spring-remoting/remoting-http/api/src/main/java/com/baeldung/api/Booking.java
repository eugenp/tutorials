package com.baeldung.api;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Booking implements Serializable {

    private int costInCent;
    private int etaInSeconds;
    private String bookingCode;
    private LocalDateTime pickUptime;
    private Address pickUpAddress;
    private Address dropOffAddress;

    public Booking(Address pickUpAddress, LocalDateTime pickUptime, Address dropOffAddress, int costInCent, int etaInSeconds, String bookingCode) {
        this.costInCent = costInCent;
        this.etaInSeconds = etaInSeconds;
        this.bookingCode = bookingCode;
        this.pickUptime = pickUptime;
        this.pickUpAddress = pickUpAddress;
        this.dropOffAddress = dropOffAddress;
    }

    public int getCostInCent() {
        return costInCent;
    }

    public int getEtaInSeconds() {
        return etaInSeconds;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public LocalDateTime getPickUptime() {
        return pickUptime;
    }

    public Address getDropOffAddress() {
        return dropOffAddress;
    }

    @Override public String toString() {
        return String.format("Booking: pick up @ %tr in %s, drop down in %s after %d minutes, %.2f $.", pickUptime, pickUpAddress, dropOffAddress, etaInSeconds / 60, costInCent / 100.0);
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(new Booking(new Address("a", "b"),
                LocalDateTime.now(), new Address("c", "d"), 123_00, 600, "abc"));
    }
}
