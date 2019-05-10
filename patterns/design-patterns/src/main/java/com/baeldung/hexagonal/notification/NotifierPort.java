package com.baeldung.hexagonal.notification;

import com.baeldung.hexagonal.WeatherAlert;

public interface NotifierPort {
    void send(WeatherAlert weatherAlert);
    WeatherAlert getLatest();
}
