package com.baeldung.pattern.hexagonal.repository;

public class MockWeatherRepository implements WeatherRepository {
    @Override
    public double getTemperature(String location) {
        switch (location) {
        case "cluj-napoca":
            return 17;
        case "bucharest":
            return 22;
        case "new york":
            return 15;
        default:
            return 20;
        }
    }
}
