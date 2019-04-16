package com.bealdung.adapter;

import com.bealdung.Application;
import com.bealdung.adapter.feeder.HttpFeedAdapter;
import com.bealdung.adapter.notification.DesktopNotificationAdapter;
import com.bealdung.adapter.notification.PhoneNotificationAdapter;
import com.bealdung.adapter.storage.InMemoryStorage;
import com.bealdung.dto.HttpFeed;
import com.bealdung.dto.SourceType;
import com.bealdung.dto.WeatherInfo;
import com.bealdung.port.feeder.WeatherFeeder;
import com.bealdung.port.notification.Notification;
import com.bealdung.port.repository.WeatherRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class WeatherFeederTestUnitTest {

    private Application application;
    private WeatherRepository inMemoryStorage;
    private WeatherFeeder weatherFeeder;

    @Before
    public void setUp() {
        List<Notification> notificationClient = Arrays.asList(new DesktopNotificationAdapter(), new PhoneNotificationAdapter());
        inMemoryStorage = new InMemoryStorage();
        application = new Application(inMemoryStorage, notificationClient);
        weatherFeeder = new HttpFeedAdapter(application);
    }

    @Test
    public void testFeedWeather_whenHttpFeedAdded_ThenShouldReturnHttpFeed() {
        HttpFeed httpFeed = buildHttpFeed();
        weatherFeeder.feedWeather(httpFeed);
        List<WeatherInfo> weatherInfos = inMemoryStorage.getWeatherInfo(SourceType.HTTP_FEED);
        Assertions.assertThat(weatherInfos).hasSize(1);
    }

    @Test
    public void testFeedWeather_whenHttpFeedAdded_ThenShouldNotReturnRssFeed() {
        HttpFeed httpFeed = buildHttpFeed();
        weatherFeeder.feedWeather(httpFeed);
        List<WeatherInfo> weatherInfos = inMemoryStorage.getWeatherInfo(SourceType.RSS_FEED);
        Assertions.assertThat(weatherInfos).hasSize(0);
    }

    private HttpFeed buildHttpFeed() {
        HttpFeed httpFeed = new HttpFeed();
        httpFeed.setHumidity(59);
        httpFeed.setPrecipitation(20);
        httpFeed.setWindSpeed(18);
        httpFeed.setPressure(1025);
        httpFeed.setTemperature(7);
        return httpFeed;
    }
}