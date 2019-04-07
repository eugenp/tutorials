package com.baeldung.hexagonal;

public class WeatherServiceDataSourceDbImpl implements WeatherServiceDataSource {
	
	// Simulate getting current weather from database

	@Override
	public String getCurrentWeather() {
		return "sunny";
	}

}