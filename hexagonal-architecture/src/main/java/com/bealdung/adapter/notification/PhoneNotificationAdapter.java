package com.bealdung.adapter.notification;

import com.bealdung.dto.WeatherInfo;
import com.bealdung.port.notification.Notification;

public class PhoneNotificationAdapter implements Notification {

    @Override
    public void sendNotification(WeatherInfo weatherInfo) {
        System.out.println("Sending Phone notification");
    }
}
