package com.baeldung.beaninjectiontypes;


public class HourlyNews {
    private WeatherUpdate weatherUpdate;

    // constructor based bean injection
/*
    public HourlyNews(WeatherUpdate update) {
        weatherUpdate = update;
    }
*/

    // setter based bean injection
    public void setWeatherUpdate(WeatherUpdate weatherUpdate) {
        this.weatherUpdate = weatherUpdate;
    }

    // other methods for news
}