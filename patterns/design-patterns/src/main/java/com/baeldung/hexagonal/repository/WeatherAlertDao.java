package com.baeldung.hexagonal.repository;

import com.baeldung.hexagonal.WeatherAlert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock DAO for WeatherAlert.
 */
public class WeatherAlertDao implements Dao<WeatherAlert> {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherAlertDao.class);

    private List<WeatherAlert> weatherAlerts;

    public WeatherAlertDao() {
        this.weatherAlerts = new ArrayList<>();
    }

    @Override
    public List<WeatherAlert> getAll() {
        return this.weatherAlerts;
    }

    @Override
    public WeatherAlert save(WeatherAlert weatherAlert) {
        LOG.info("Saved weather alert [{}]", weatherAlert);
        this.weatherAlerts.add(weatherAlert);
        return weatherAlert;
    }

    @Override
    public WeatherAlert update(WeatherAlert weatherAlert, String[] params) {
        LOG.info("Updated weather alert [{}]", weatherAlert);
        this.weatherAlerts.add(weatherAlert);
        return weatherAlert;
    }

    @Override
    public boolean delete(WeatherAlert weatherAlert) {
        LOG.info("Deleted weather alert [{}]", weatherAlert);
        this.weatherAlerts.remove(weatherAlert);
        return true;
    }
}
