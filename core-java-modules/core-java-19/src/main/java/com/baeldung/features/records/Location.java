package com.baeldung.features.records;

public record Location(String name, GPSPoint gpsPoint) implements ILocation {
}

