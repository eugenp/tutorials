package com.baeldung.hexagonal.repository;

import com.baeldung.hexagonal.WeatherAlert;

import java.util.List;

public class DatabaseRepository implements RepositoryPort {

    private WeatherAlertDao weatherAlertDao;

    public DatabaseRepository() {
        this.weatherAlertDao = new WeatherAlertDao();
    }

    @Override
    public WeatherAlert save(WeatherAlert weatherAlert) {
        return this.weatherAlertDao.save(weatherAlert);
    }

    @Override
    public List<WeatherAlert> findAll() {
        return this.weatherAlertDao.getAll();
    }
}
