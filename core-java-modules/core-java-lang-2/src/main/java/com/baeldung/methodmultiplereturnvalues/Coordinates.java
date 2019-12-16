package com.baeldung.methodmultiplereturnvalues;

public class Coordinates {

    private double longitude;
    private double latitude;
    private Coordinates nearbyLocation;
    
    public Coordinates() {}
    
    public Coordinates(double longitude, double latitude, Coordinates nearbyLocation) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.nearbyLocation = nearbyLocation;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Coordinates getNearbyLocation() {
        return nearbyLocation;
    }

    public void setNearbyLocation(Coordinates nearbyLocation) {
        this.nearbyLocation = nearbyLocation;
    }

}
