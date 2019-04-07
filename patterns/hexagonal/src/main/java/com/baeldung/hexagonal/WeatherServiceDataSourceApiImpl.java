package com.baeldung.hexagonal;

public class WeatherServiceDataSourceApiImpl implements WeatherServiceDataSource {
	
	// Simulate getting current weather from api

	@Override
	public String getCurrentWeather() {
		return "rainy";
	}

}