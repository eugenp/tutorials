package com.baeldung.osgi.service.service;

public class GeocodeException extends Exception {
    public GeocodeException(Exception e) {
        super(e);
    }
}
