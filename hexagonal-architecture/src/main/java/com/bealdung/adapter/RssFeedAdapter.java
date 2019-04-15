package com.bealdung.adapter;

import com.bealdung.Application;
import com.bealdung.dto.BaseWeatherDTO;
import com.bealdung.dto.RssFeed;
import com.bealdung.dto.SourceType;
import com.bealdung.dto.WeatherInfo;
import com.bealdung.port.WeatherFeeder;

public class RssFeedAdapter implements WeatherFeeder {

    private Application application;

    public RssFeedAdapter(Application application) {
        this.application = application;
    }

    public void feedWeather(BaseWeatherDTO feed) {
        RssFeed httpFeed = (RssFeed) feed;
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setHumidity(Double.toString(httpFeed.getHumidity()));
        weatherInfo.setPrecipitation(Double.toString(httpFeed.getPrecipitation()));
        weatherInfo.setPressure(Double.toString(httpFeed.getPressure()));
        weatherInfo.setTemperature(Double.toString(httpFeed.getTemperature()));
        weatherInfo.setWindSpeed(Double.toString(httpFeed.getWindSpeed()));
        weatherInfo.setSource(SourceType.RSS_FEED);
        application.saveWeather(weatherInfo);
    }
}
