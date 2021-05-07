package com.baeldung.pattern.hexagonal.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MockWeatherRepositoryUnitTest {

    private final WeatherRepository tested = new MockWeatherRepository();

    @Test
    void givenLocationClujNapoca_whenGetTemperature_thenExpect17() {
        double result = tested.getTemperature("cluj-napoca");

        assertEquals(17, result);
    }

    @Test
    void givenLocationBucharest_whenGetTemperature_thenExpect22() {
        double result = tested.getTemperature("bucharest");

        assertEquals(22, result);
    }

    @Test
    void givenLocationNewYork_whenGetTemperature_thenExpect15() {
        double result = tested.getTemperature("new york");

        assertEquals(15, result);
    }

    @Test
    void givenLocationDefault_whenGetTemperature_thenExpect20() {
        double result = tested.getTemperature("default");

        assertEquals(20, result);
    }

}