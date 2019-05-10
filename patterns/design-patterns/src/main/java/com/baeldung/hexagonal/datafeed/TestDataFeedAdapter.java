package com.baeldung.hexagonal.datafeed;

import com.baeldung.hexagonal.WeatherAlert;

import java.util.Date;

public class TestDataFeedAdapter {

    private DataFeeder dataFeeder;

    public TestDataFeedAdapter(DataFeeder dataFeeder) {
        this.dataFeeder = dataFeeder;
    }

    public void runTest() {
        WeatherAlert weatherAlert = new WeatherAlert();
        weatherAlert.setWeatherAlertDate(new Date());
        weatherAlert.setWeatherAlertType(WeatherAlert.WeatherAlertType.HAIL);
        this.dataFeeder.trigger(weatherAlert);
    }

}
