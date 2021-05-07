package com.baeldung.pattern.hexagonal.service;

import com.baeldung.pattern.hexagonal.repository.WeatherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class WeatherForecasterServiceUnitTest {

    @Mock
    private WeatherRepository repository;
    @InjectMocks
    private WeatherForecasterService tested;

    @Test
    void givenLocation10_whenForecast_thenExpect10() {
        when(repository.getTemperature("location")).thenReturn(10d);

        double result = tested.forecast("location");
        assertEquals(10d, result);
    }

    @Test
    void givenNullLocation_whenForecast_thenExpectEmptyString() {
        when(repository.getTemperature("")).thenReturn(10d);

        double result = tested.forecast(null);
        assertEquals(10d, result);
    }
}