package com.baeldung.sealed.classes;

public final class Truck extends Vehicle implements Service {

    private int loadCapacity;

    public int getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(int loadCapacity) {
        this.loadCapacity = loadCapacity;
    }

    @Override
    public int getMaxServiceIntervalInMonths() {
        return 18;
    }

    @Override
    public int getMaxDistanceBetweenServicesInKilometers() {
        return 150000;
    }

}
