package com.baeldung.methodmultiplereturnvalues;

public class Coordinates {

    private double longitude;
    private double latitude;
    private String placeName;
    
    public Coordinates() {}
    
    public Coordinates(double longitude, double latitude, String placeName) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.placeName = placeName;
    }

    public double calculateDistance(Coordinates c) {
        
        double s1 = Math.abs(this.longitude - c.longitude);
        double s2 = Math.abs(this.latitude - c.latitude);
        
        return Math.hypot(s1, s2);
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

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
    
}
