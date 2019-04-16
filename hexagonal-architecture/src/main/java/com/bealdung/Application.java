package com.bealdung;

import com.bealdung.dto.WeatherInfo;
import com.bealdung.port.notification.Notification;
import com.bealdung.port.repository.WeatherRepository;

import java.util.Collection;
import java.util.List;

public class Application {
    private WeatherRepository repository;
    private Collection<Notification> notificationClient;

    public Application(WeatherRepository repository, List<Notification> notificationClient) {
        this.repository = repository;
        this.notificationClient = notificationClient;
    }

    public void saveWeather(WeatherInfo weatherInfo) {
        WeatherInfo savedEntiry = repository.save(weatherInfo);
        notificationClient.forEach(notification -> notification.sendNotification(savedEntiry));
    }
}
