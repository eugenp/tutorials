package com.baeldung.pattern.hexagonal.repository;

public class RepositoryFactory {
    private RepositoryFactory() {
        super();
    }

    public static WeatherRepository getMockWeatherRepository() {
        return new MockWeatherRepository();
    }
}
