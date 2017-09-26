package com.baeldung.osgi.service.service;

public interface GeocodingService {
    Coord geocode(String address) throws GeocodeException;
}
