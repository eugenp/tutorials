package com.baeldung.hexagonal.repository;

import com.baeldung.hexagonal.WeatherAlert;

import java.util.List;

public interface RepositoryPort {
    WeatherAlert save(WeatherAlert weatherAlert);
    List<WeatherAlert> findAll();
}
