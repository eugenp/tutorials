package com.baeldung.sealed.classes;

public sealed class VehicleImpl permits CarImpl, TruckImpl {

    private String registrationNumber;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

}
