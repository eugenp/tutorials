package com.bealdung.port.feeder;

import com.bealdung.dto.BaseWeatherDTO;

public interface WeatherFeeder {
    void feedWeather(BaseWeatherDTO feed);
}
