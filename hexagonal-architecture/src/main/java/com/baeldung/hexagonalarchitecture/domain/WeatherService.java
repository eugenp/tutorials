package com.baeldung.hexagonalarchitecture.domain;

public interface WeatherService {
    void storeAndNotify(Temperature temperature);
}
