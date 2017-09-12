package com.baeldung.osgi.geocoding;

public class GeocodeException extends Exception {
    public GeocodeException(Exception e) {
        super(e);
    }
}
