package com.baeldung.testing.easymock;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ForecastProcessor {
    private WeatherService weatherService;
    
    public Location findLocationWithMaximumTemperature(List<String> locationNames) {
        
        List<Location> locations = locationNames.stream().map(name -> new Location(name)).collect(Collectors.toList());
        
        weatherService.populateTemperature(locations);
        
        Collections.sort(locations, (left, right) -> right.getMaximumTemparature()
                .compareTo(left.getMaximumTemparature()));
        
        return locations.get(0);
    }

    public WeatherService getWeatherService() {
        return weatherService;
    }

    public void setWeatherService(WeatherService weatherService) {
        this.weatherService = weatherService;
    }    
}
