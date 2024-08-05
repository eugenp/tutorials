package com.baeldung.sorting;

import com.fasterxml.jackson.annotation.JsonProperty;

class SolarEvent {

    @JsonProperty("event_name")
    private String eventName;
    @JsonProperty("date")
    private String date;
    @JsonProperty("coordinates")
    private Coordinates coordinates;
    @JsonProperty("type")
    private String type;
    @JsonProperty("class")
    private String eventClass;
    @JsonProperty("size")
    private String size;
    @JsonProperty("speed_km_per_s")
    private int speedKmPerS;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEventClass() {
        return eventClass;
    }

    public void setEventClass(String eventClass) {
        this.eventClass = eventClass;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getSpeedKmPerS() {
        return speedKmPerS;
    }

    public void setSpeedKmPerS(int speedKmPerS) {
        this.speedKmPerS = speedKmPerS;
    }
}

class Coordinates {

    @JsonProperty("latitude")
    private double latitude;

    @JsonProperty("longitude")
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
