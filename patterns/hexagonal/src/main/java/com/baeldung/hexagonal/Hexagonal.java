package com.baeldung.hexagonal;

public class Hexagonal {

	public static void main(String args[]) {
		
		WeatherServiceImpl weatherService = new WeatherServiceImpl();
		
		String currentWeather = weatherService.getCurrentWeather();
		
		System.out.println("The current weather is " + currentWeather + "!");
		
	}
	
}