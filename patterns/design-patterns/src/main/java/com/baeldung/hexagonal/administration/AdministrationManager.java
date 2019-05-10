package com.baeldung.hexagonal.administration;

import com.baeldung.hexagonal.WeatherAlert;
import com.baeldung.hexagonal.WeatherAlertManager;

public class AdministrationManager implements AdministrationPort {

    private WeatherAlertManager manager;

    public AdministrationManager(WeatherAlertManager manager) {
        this.manager = manager;
    }

    @Override
    public void add(WeatherAlert weatherAlert) {
        this.manager.process(weatherAlert);
    }
}
