package com.baeldung.hexagonal.administration;

import com.baeldung.hexagonal.WeatherAlert;

import java.util.Date;

public class TestAdministrationAdapter {

    private AdministrationManager administrationManager;

    public TestAdministrationAdapter(AdministrationManager administrationManager) {
        this.administrationManager = administrationManager;
    }

    public void runTest() {
        WeatherAlert weatherAlert = new WeatherAlert();
        weatherAlert.setWeatherAlertDate(new Date());
        weatherAlert.setWeatherAlertType(WeatherAlert.WeatherAlertType.TORNADO);
        this.administrationManager.add(weatherAlert);
    }
}
