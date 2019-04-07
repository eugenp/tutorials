package com.baeldung.hexagonal;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class HexagonalIntegrationTest {

	@Test
	void givenWeatherServiceDataSourceDbImpl_whenGettingCurrentWeather_thenSunny() {
		
		WeatherServiceDataSource weatherServiceDataSource = new WeatherServiceDataSourceDbImpl();
		
		assertEquals(weatherServiceDataSource.getCurrentWeather(), "sunny");
		
	}
	
	@Test
	void givenWeatherServiceDataSourceApiImpl_whenGettingCurrentWeather_thenRainy() {
		
		WeatherServiceDataSource weatherServiceDataSource = new WeatherServiceDataSourceApiImpl();
		
		assertEquals(weatherServiceDataSource.getCurrentWeather(), "rainy");
		
	}

}