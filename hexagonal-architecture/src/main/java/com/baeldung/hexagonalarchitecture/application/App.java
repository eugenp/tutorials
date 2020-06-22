package com.baeldung.hexagonalarchitecture.application;

import com.baeldung.hexagonalarchitecture.domain.NotificationSender;
import com.baeldung.hexagonalarchitecture.domain.Temperature;
import com.baeldung.hexagonalarchitecture.domain.TemperatureRepository;
import com.baeldung.hexagonalarchitecture.domain.WeatherService;
import com.baeldung.hexagonalarchitecture.domain.WeatherServiceImpl;
import com.baeldung.hexagonalarchitecture.infrastructure.mail.EmailNotificationInMemory;
import com.baeldung.hexagonalarchitecture.infrastructure.repository.TemperatureRepositoryInMemory;

public class App {

    private final WeatherService weatherService;

    public App() {
        NotificationSender notificationSender = new EmailNotificationInMemory();
        TemperatureRepository temperatureRepository = new TemperatureRepositoryInMemory();
        this.weatherService = new WeatherServiceImpl(notificationSender, temperatureRepository);
    }

    void run() {
        weatherService.storeAndNotify(new Temperature("city-1", 22));
    }

    public static void main(String[] args) {
        new App().run();
    }
}
