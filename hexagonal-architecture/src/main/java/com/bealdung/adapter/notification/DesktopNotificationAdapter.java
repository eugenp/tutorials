package com.bealdung.adapter.notification;

import com.bealdung.dto.WeatherInfo;
import com.bealdung.port.notification.Notification;

public class DesktopNotificationAdapter implements Notification {
    @Override
    public void sendNotification(WeatherInfo weatherInfo) {
        System.out.println("Sending desktop notification");
    }
}
