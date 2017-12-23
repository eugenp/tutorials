package com.baeldung.defaultstaticinterfacemethods.model;

public interface Vehicle {

    String getBrand();

    String speedUp();

    String slowDown();

    default String turnAlarmOn() {
        return "Turning the vehice alarm on.";
    }

    default String turnAlarmOff() {
        return "Turning the vehicle alarm off.";
    }

    static int getHorsePower(int rpm, int torque) {
        return (rpm * torque) / 5252;
    }
}