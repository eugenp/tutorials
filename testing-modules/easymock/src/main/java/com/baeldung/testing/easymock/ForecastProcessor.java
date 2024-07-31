package com.baeldung.testing.easymock;

import java.math.BigDecimal;

public class ForecastProcessor {
    private WeatherService weatherService;
    
    public BigDecimal getMaximumTemperature(String locationName) {
        
        Location location = new Location(locationName);
        
        try {
            weatherService.populateTemperature(location);
        } catch (ServiceUnavailableException e) {
           return null;
        }
        
        return location.getMaximumTemparature();
    }

    public WeatherService getWeatherService() {
        return weatherService;
    }

    public void setWeatherService(WeatherService weatherService) {
        this.weatherService = weatherService;
    }    
}
