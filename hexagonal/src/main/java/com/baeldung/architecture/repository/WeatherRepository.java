package com.baeldung.architecture.repository;

import com.baeldung.architecture.model.Weather;

public interface WeatherRepository {

    public Weather getTodaysForecast();

}
