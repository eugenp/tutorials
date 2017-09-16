package com.baeldung.osgi.geocoding.service;

public class GeocodeException extends Exception {
    public GeocodeException(Exception e) {
        super(e);
    }
}
