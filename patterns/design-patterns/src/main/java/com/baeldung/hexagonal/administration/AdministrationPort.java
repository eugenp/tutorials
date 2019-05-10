package com.baeldung.hexagonal.administration;

import com.baeldung.hexagonal.WeatherAlert;

public interface AdministrationPort {
    void add(WeatherAlert weatherAlert);
}
