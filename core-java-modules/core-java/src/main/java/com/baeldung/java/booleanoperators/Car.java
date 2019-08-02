package com.baeldung.java.booleanoperators;

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
}
