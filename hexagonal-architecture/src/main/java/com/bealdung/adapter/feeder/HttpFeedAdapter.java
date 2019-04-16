package com.bealdung.adapter.feeder;

import com.bealdung.Application;
import com.bealdung.dto.BaseWeatherDTO;
import com.bealdung.dto.HttpFeed;
import com.bealdung.dto.SourceType;
import com.bealdung.dto.WeatherInfo;
import com.bealdung.port.feeder.WeatherFeeder;

public class HttpFeedAdapter implements WeatherFeeder {

    private Application application;

    public HttpFeedAdapter(Application application) {
        this.application = application;
    }

    public void feedWeather(BaseWeatherDTO feed) {
        HttpFeed httpFeed = (HttpFeed) feed;
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setHumidity(Float.toString(httpFeed.getHumidity()));
        weatherInfo.setPrecipitation(Float.toString(httpFeed.getPrecipitation()));
        weatherInfo.setPressure(Float.toString(httpFeed.getPressure()));
        weatherInfo.setTemperature(Float.toString(httpFeed.getTemperature()));
        weatherInfo.setWindSpeed(Float.toString(httpFeed.getWindSpeed()));
        weatherInfo.setSource(SourceType.HTTP_FEED);
        application.saveWeather(weatherInfo);
    }
}
