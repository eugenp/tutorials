package com.baeldung.abstractconstructors.parametrized;

public abstract class Car {

    private int distance;

    private Car(int distance) {
        this.distance = distance;
    }

    public Car() {
        this(0);
        System.out.println("Car default constructor");
    }

    abstract String getInformation();

    protected void display() {
        String info = new StringBuilder(getInformation())
          .append("\nDistance: " + getDistance())
          .toString();
        System.out.println(info);
    }

    public int getDistance() {
        return distance;
    }
}
