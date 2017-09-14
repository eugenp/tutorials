package com.baeldung.osgi.geocoding;

public interface GeocodingService {
    Coord geocode(String address) throws GeocodeException;
}
