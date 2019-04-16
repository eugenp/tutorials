package com.bealdung.port.notification;

import com.bealdung.dto.WeatherInfo;

public interface Notification {
    void sendNotification(WeatherInfo weatherInfo);
}
