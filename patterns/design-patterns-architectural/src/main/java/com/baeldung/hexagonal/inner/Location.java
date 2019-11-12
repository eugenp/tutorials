package com.baeldung.hexagonal.inner;

public class Location {

    private double longitude;
    private double latitude;

    public Location(double latitude, double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double longitude() {
        return longitude;
    }

    public double latitude() {
        return latitude;
    }

    @Override
    public String toString() {
        return "Location{" + "longitude=" + longitude + ", latitude=" + latitude + '}';
    }
}
