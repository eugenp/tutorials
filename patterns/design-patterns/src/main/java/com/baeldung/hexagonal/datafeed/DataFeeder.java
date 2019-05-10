package com.baeldung.hexagonal.datafeed;

import com.baeldung.hexagonal.WeatherAlert;
import com.baeldung.hexagonal.WeatherAlertManager;

public class DataFeeder implements DataFeedPort {

    private WeatherAlertManager manager;

    public DataFeeder(WeatherAlertManager manager) {
        this.manager = manager;
    }

    @Override
    public void trigger(WeatherAlert weatherAlert) {
        this.manager.process(weatherAlert);
    }

}
