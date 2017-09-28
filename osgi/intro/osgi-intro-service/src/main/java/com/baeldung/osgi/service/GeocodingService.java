package com.baeldung.osgi.service;

public interface GeocodingService {
    Coord geocode(String address) throws GeocodeException;
}
