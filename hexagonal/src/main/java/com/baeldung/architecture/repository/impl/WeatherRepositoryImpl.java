package com.baeldung.architecture.repository.impl;

import java.util.Calendar;

import org.springframework.stereotype.Repository;

import com.baeldung.architecture.model.Weather;
import com.baeldung.architecture.repository.WeatherRepository;

@Repository
public class WeatherRepositoryImpl implements WeatherRepository {

    @Override
    public Weather getTodaysForecast() {

        Weather dummyWeather = new Weather();
        dummyWeather.setCurrentTime(Calendar.getInstance().getTime());
        dummyWeather.setHumidity(68);
        dummyWeather.setPlaceName("Qatar");
        dummyWeather.setPrecipitation(10);
        dummyWeather.setWind(11);
        return dummyWeather;
    }

}
