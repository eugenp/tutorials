package com.bealdung.adapter;

import com.bealdung.Application;
import com.bealdung.dto.BaseWeatherDTO;
import com.bealdung.dto.SourceType;
import com.bealdung.dto.TelemetryFeed;
import com.bealdung.dto.WeatherInfo;
import com.bealdung.port.WeatherFeeder;

public class TelemetryFeedAdapter implements WeatherFeeder {
    private Application application;

    public TelemetryFeedAdapter(Application application) {
        this.application = application;
    }

    public void feedWeather(BaseWeatherDTO feed) {
        TelemetryFeed httpFeed = (TelemetryFeed) feed;
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setHumidity(Integer.toString(httpFeed.getHumidity()));
        weatherInfo.setPrecipitation(Integer.toString(httpFeed.getPrecipitation()));
        weatherInfo.setPressure(Integer.toString(httpFeed.getPressure()));
        weatherInfo.setTemperature(Integer.toString(httpFeed.getTemperature()));
        weatherInfo.setWindSpeed(Integer.toString(httpFeed.getWindSpeed()));
        weatherInfo.setSource(SourceType.TELEMETRY_FEED);
        application.saveWeather(weatherInfo);
    }
}
