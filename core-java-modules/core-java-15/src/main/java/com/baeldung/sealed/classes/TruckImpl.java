package com.baeldung.sealed.classes;

public non-sealed class TruckImpl extends VehicleImpl {

    private int loadCapacity;

    public int getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(int loadCapacity) {
        this.loadCapacity = loadCapacity;
    }

}
