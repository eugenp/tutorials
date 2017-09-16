package com.baeldung.osgi.geocoding.service;

public interface GeocodingService {
    Coord geocode(String address) throws GeocodeException;
}
