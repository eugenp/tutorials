package com.baeldung.tutorials.openapi.generatehttpclients;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.baeldung.tutorials.openapi.generatehttpclients.api.WeatherApi;
import com.baeldung.tutorials.openapi.generatehttpclients.api.model.WeatherResponse;
import com.baeldung.tutorials.openapi.generatehttpclients.api.model.WeatherResponseCurrent;
import com.baeldung.tutorials.openapi.generatehttpclients.service.GetWeatherService;

@ExtendWith(MockitoExtension.class)
class WeatherApiUnitTest {

    private GetWeatherService weatherService;

    @Mock
    private WeatherApi weatherApi;

    @BeforeEach
    void setUp() {
        weatherService = new GetWeatherService(weatherApi);
    }

    @Test
    void givenOkStatus_whenCallingWeatherApi_thenReturnCurrentWeather() {
        when(weatherApi.getCurrentWeather("London", "celsius")).thenReturn(
            new ResponseEntity<>(new WeatherResponse().current(new WeatherResponseCurrent(455d, WeatherResponseCurrent.UnitsEnum.CELSIUS)), HttpStatus.OK));

        var weather = weatherService.getCurrentWeather("London", "celsius");

        assertThat(weather.getCurrent()
            .getTemperature()).isEqualTo(455d);
        assertThat(weather.getCurrent()
            .getUnits()).isEqualTo(WeatherResponseCurrent.UnitsEnum.CELSIUS);
    }

    @Test
    void givenBadRequestStatus_whenCallingWeatherApi_thenReturnError() {
        when(weatherApi.getCurrentWeather("London", "celsius")).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        assertThrows(RuntimeException.class, () -> weatherService.getCurrentWeather("London", "celsius"));
    }
}
