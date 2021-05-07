package com.baeldung.pattern.hexagonal.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RepositoryFactoryUnitTest {
    @Test
    void whenGetMockWeatherRepository_thenExpectMockWeatherRepositoryInstance() {
        WeatherRepository result = RepositoryFactory.getMockWeatherRepository();

        assertTrue(result instanceof MockWeatherRepository);
    }
}