package com.baeldung.booleanoperators;

public class Car {

    private boolean diesel;
    private boolean manual;

    public Car(boolean diesel, boolean manual) {
        this.diesel = diesel;
        this.manual = manual;
    }

    public boolean isDiesel() {
        return diesel;
    }

    public boolean isManual() {
        return manual;
    }

    static Car dieselAndManualCar() {
        return new Car(true, true);
    }

    static Car dieselAndAutomaticCar() {
        return new Car(true, false);
    }

    static Car oilAndManualCar() {
        return new Car(false, true);
    }

    static Car oilAndAutomaticCar() {
        return new Car(false, false);
    }
}
