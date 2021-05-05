package com.baeldung.pattern.hexagonal.repository;

public interface WeatherRepository {
    double getTemperature(String location);
}
