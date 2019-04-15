package com.bealdung.port;

import com.bealdung.dto.BaseWeatherDTO;

public interface WeatherFeeder {
    void feedWeather(BaseWeatherDTO feed);
}
