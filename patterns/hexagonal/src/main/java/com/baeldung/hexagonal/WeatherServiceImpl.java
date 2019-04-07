package com.baeldung.hexagonal;

public class WeatherServiceImpl implements WeatherService {
	
	private WeatherServiceDataSource weatherServiceDataSource;
	
	public WeatherServiceImpl() {
		this.weatherServiceDataSource = new WeatherServiceDataSourceDbImpl();
	}
	
	@Override
	public String getCurrentWeather() {
		return this.weatherServiceDataSource.getCurrentWeather();
	}

}