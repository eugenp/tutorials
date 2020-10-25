package com.baeldung.sealed.classes;

public non-sealed class CarImpl extends VehicleImpl {

    private int numberOfSeats;

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

}
