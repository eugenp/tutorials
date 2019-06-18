package com.baeldung.testing.easymock;

import java.util.List;

public interface WeatherService {
    void populateTemperature(List<Location> location);
}
