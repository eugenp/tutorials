package com.baeldung.api;

import java.io.Serializable;
import java.util.Date;

public class Booking implements Serializable {

    private int costInCent;
    private int etaInSeconds;
    private String bookingCode;
    private Date pickUptime;
    private Address pickUpAddress;
    private Address dropDownAddress;

    public Booking(Address pickUpAddress, Date pickUptime, Address dropDownAddress, int costInCent, int etaInSeconds, String bookingCode) {
        this.costInCent = costInCent;
        this.etaInSeconds = etaInSeconds;
        this.bookingCode = bookingCode;
        this.pickUptime = pickUptime;
        this.pickUpAddress = pickUpAddress;
        this.dropDownAddress = dropDownAddress;
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

    public Date getPickUptime() {
        return pickUptime;
    }

    @Override public String toString() {
        return String.format("Booking: pick up @ %s in %s, drop down in %s after %d minutes, %s $.", pickUptime, pickUpAddress, dropDownAddress, etaInSeconds, costInCent);
    }
}
