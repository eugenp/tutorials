package com.baeldung.hexagonalarchitecture.domain;

public class WeatherServiceImpl implements WeatherService {

    private NotificationSender notificationSender;
    private TemperatureRepository temperatureRepository;

    public WeatherServiceImpl(NotificationSender notificationSender,
        TemperatureRepository temperatureRepository) {
        this.notificationSender = notificationSender;
        this.temperatureRepository = temperatureRepository;
    }

    @Override public void storeAndNotify(Temperature temperature) {
        temperatureRepository.store(temperature);
        notificationSender.send(temperature);
    }
}
