package com.baeldung.testing.easymock;

public interface WeatherService {
    void populateTemperature(Location location) throws ServiceUnavailableException;
}
